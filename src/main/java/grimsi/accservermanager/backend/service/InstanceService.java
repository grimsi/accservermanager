package grimsi.accservermanager.backend.service;

import com.google.gson.Gson;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.entity.Instance;
import grimsi.accservermanager.backend.enums.InstanceState;
import grimsi.accservermanager.backend.exception.ConflictException;
import grimsi.accservermanager.backend.exception.IllegalInstanceStateException;
import grimsi.accservermanager.backend.exception.InstanceNotStoppedException;
import grimsi.accservermanager.backend.exception.NotFoundException;
import grimsi.accservermanager.backend.repository.InstanceRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstanceService {

    @Autowired
    JsonSchemaService jsonSchemaService;

    @Autowired
    EventService eventService;

    @Autowired
    FileSystemService fileSystemService;

    @Autowired
    ContainerService containerService;

    @Autowired
    InstanceRepository instanceRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Gson gson;

    private List<SseEmitter> sseEmitters = new ArrayList<>();

    public List<InstanceDto> findAll() {
        List<Instance> instances = instanceRepository.findAll();
        return mapper.map(instances, new TypeToken<List<InstanceDto>>() {
        }.getType());
    }

    public InstanceDto findById(String id) {
        Instance instance = instanceRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertToDto(instance);
    }

    public InstanceDto create(InstanceDto instanceDto) {
        Instance instance = convertToEntity(instanceDto);

        instance.state = InstanceState.STOPPED;
        instance.restartRequired = false;

        instanceDto = convertToDto(instance);
        instanceDto.setEvent(eventService.findById(instanceDto.getEvent().getId()));

        checkIfPortsAreInUse(instanceDto);

        instanceDto = save(instanceDto);

        try {
            fileSystemService.createInstanceFolder(instanceDto);
            containerService.deployInstance(instanceDto);
        } catch (Exception e) {
            deleteById(instanceDto.getId());
            throw e;
        }

        instanceDto = save(instanceDto);
        emitNewEvent("create", gson.toJson(instanceDto));
        return instanceDto;
    }

    public InstanceDto updateById(String id, InstanceDto newInstanceDto) {
        InstanceDto oldInstanceDto = findById(id);

        newInstanceDto.setEvent(eventService.findById(newInstanceDto.getEvent().getId()));
        newInstanceDto.setRestartRequired(true);

        /* Set the values that the user is not allowed to change */
        newInstanceDto.setId(id);
        newInstanceDto.setState(oldInstanceDto.getState());
        newInstanceDto.setContainer(oldInstanceDto.getContainer());

        checkIfPortsAreInUse(newInstanceDto);

        newInstanceDto = save(newInstanceDto);
        emitNewEvent("update", gson.toJson(newInstanceDto));
        return newInstanceDto;
    }

    public void deleteById(String id) {
        InstanceDto instance = findById(id);

        if (instance.getState() != InstanceState.STOPPED && instance.getState() != InstanceState.CRASHED) {
            throw new InstanceNotStoppedException(instance.getId(), instance.getState());
        }

        fileSystemService.deleteInstanceFolder(instance);
        containerService.deleteInstance(instance);
        instanceRepository.deleteById(id);

        emitNewEvent("delete", id);
    }

    public void startInstance(String instanceId) {
        InstanceDto instanceDto = findById(instanceId);

        if (instanceDto.getState() == InstanceState.RUNNING) {
            throw new IllegalInstanceStateException("start", instanceId, instanceDto.getState());
        }

        if (instanceDto.isRestartRequired()) {
            containerService.deleteContainer(instanceDto.getContainer());
            fileSystemService.updateInstanceFolder(instanceDto);
            containerService.deployInstance(instanceDto);
            instanceDto.setRestartRequired(false);
            emitNewEvent("update", gson.toJson(instanceDto));
        }

        containerService.startInstance(instanceDto);
        instanceDto.setState(InstanceState.RUNNING);

        instanceDto = save(instanceDto);

        emitNewEvent("start", instanceDto.getId());
    }

    public void stopInstance(String instanceId) {
        InstanceDto instanceDto = findById(instanceId);

        if (instanceDto.getState() == InstanceState.STOPPED) {
            throw new IllegalInstanceStateException("stop", instanceId, instanceDto.getState());
        }

        containerService.stopInstance(instanceDto);
        instanceDto.setState(InstanceState.STOPPED);

        instanceDto = save(instanceDto);

        emitNewEvent("stop", instanceDto.getId());
    }

    public void pauseInstance(String instanceId) {
        InstanceDto instanceDto = findById(instanceId);

        if (instanceDto.getState() != InstanceState.RUNNING) {
            throw new IllegalInstanceStateException("pause", instanceId, instanceDto.getState());
        }

        containerService.pauseInstance(instanceDto);
        instanceDto.setState(InstanceState.PAUSED);

        instanceDto = save(instanceDto);

        emitNewEvent("pause", instanceDto.getId());
    }

    public void resumeInstance(String instanceId) {
        InstanceDto instanceDto = findById(instanceId);

        if (instanceDto.getState() != InstanceState.PAUSED) {
            throw new IllegalInstanceStateException("resume", instanceId, instanceDto.getState());
        }

        containerService.resumeInstance(instanceDto);
        instanceDto.setState(InstanceState.RUNNING);

        instanceDto = save(instanceDto);

        emitNewEvent("resume", instanceDto.getId());
    }

    @SuppressWarnings("Duplicates")
    public SseEmitter createNewEventEmitter() {
        SseEmitter emitter = new SseEmitter();
        sseEmitters.add(emitter);

        emitter.onCompletion(() -> sseEmitters.remove(emitter));
        emitter.onTimeout(() -> sseEmitters.remove(emitter));
        emitter.onError((throwable) -> sseEmitters.remove(emitter));

        return emitter;
    }

    @SuppressWarnings("Duplicates")
    private void emitNewEvent(String name, String data) {
        sseEmitters.forEach(sseEmitter -> {
            try {
                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .data(data)
                        .name(name);
                sseEmitter.send(event);
            } catch (Exception ex) {
                sseEmitter.completeWithError(ex);
            }
        });
    }

    public String getJsonSchema() {
        String schema = jsonSchemaService.getJsonSchema(InstanceDto.class);
        if (schema == null) {
            throw new NullPointerException("Error parsing JSON schema");
        }
        return schema;
    }

    @SuppressWarnings("Duplicates")
    public InstanceDto save(InstanceDto instanceDto) {
        Instance instance = convertToEntity(instanceDto);

        try {
            instance = instanceRepository.save(instance);
        } catch (DuplicateKeyException e) {
            throw new ConflictException("Name '" + instance.name + "' is already in use.");
        }

        return convertToDto(instance);
    }

    public List<InstanceDto> findInstancesByEventId(String eventId) {
        List<Instance> matchingInstances = instanceRepository.findAllByEvent_Id(eventId).get();
        return convertToDto(matchingInstances);
    }

    public InstanceDto findByName(String name) {
        Instance instance = instanceRepository.findByName(name).orElseThrow(NotFoundException::new);
        return convertToDto(instance);
    }

    public int getActiveInstanceCount() {
        return instanceRepository.findAllByState(InstanceState.RUNNING).orElseThrow(NotFoundException::new).size();
    }

    public boolean isEventInUse(String eventId) {
        return !findInstancesByEventId(eventId).isEmpty();
    }

    private List<InstanceDto> getInstancesByPorts(int tcpPort, int udpPort) {
        List<Instance> matchingInstances = instanceRepository.findAllByConfiguration_TcpPortOrConfiguration_UdpPort(tcpPort, udpPort).get();
        return convertToDto(matchingInstances);
    }

    private void checkIfPortsAreInUse(InstanceDto instanceDto) {

        int tcpPort = instanceDto.getConfiguration().getTcpPort();
        int udpPort = instanceDto.getConfiguration().getUdpPort();

        List<InstanceDto> instancesWithSamePorts = getInstancesByPorts(tcpPort, udpPort);

        if (instancesWithSamePorts.isEmpty()) {
            return;
        }

        if (instanceDto.getId() == null) {
            throw new ConflictException("At least one of the ports is already used by another instance.");
        }

        boolean arePortsInUse = instancesWithSamePorts.stream().noneMatch(i -> (i.getId().equals(instanceDto.getId())));

        if (arePortsInUse) {
            throw new ConflictException("At least one of the ports is already used by another instance.");
        }

    }

    private InstanceDto convertToDto(Instance instance) {
        return mapper.map(instance, InstanceDto.class);
    }

    private List<InstanceDto> convertToDto(List<Instance> instanceDtos) {
        return instanceDtos.stream().map(instance -> mapper.map(instance, InstanceDto.class)).collect(Collectors.toList());
    }

    private Instance convertToEntity(InstanceDto instanceDto) {
        return mapper.map(instanceDto, Instance.class);
    }

    private List<Instance> convertToEntity(List<Instance> instances) {
        return instances.stream().map(instanceDto -> mapper.map(instanceDto, Instance.class)).collect(Collectors.toList());
    }
}
