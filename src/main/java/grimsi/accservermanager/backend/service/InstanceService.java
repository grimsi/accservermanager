package grimsi.accservermanager.backend.service;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstanceService {

    @Autowired
    JsonSchemaService jsonSchemaService;

    @Autowired
    ConfigService configService;

    @Autowired
    FileSystemService fileSystemService;

    @Autowired
    ContainerService containerService;

    @Autowired
    InstanceRepository instanceRepository;

    @Autowired
    ModelMapper mapper;

    public List<InstanceDto> findAll() {
        List<Instance> instances = instanceRepository.findAll();
        return mapper.map(instances, new TypeToken<List<InstanceDto>>() {
        }.getType());
    }

    public InstanceDto findById(String id) {
        Instance instance = instanceRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertToDto(instance);
    }

    public List<InstanceDto> findByName(String name){
        List<Instance> instances = instanceRepository.findAllByName(name).orElseThrow(NotFoundException::new);
        return convertToDto(instances);
    }

    public void deleteById(String id) {
        InstanceDto instance = findById(id);

        if(instance.getState() != InstanceState.STOPPED && instance.getState() != InstanceState.CRASHED){
            throw new InstanceNotStoppedException(instance.getId(), instance.getState());
        }

        fileSystemService.deleteInstanceFolder(instance);
        containerService.deleteInstance(instance);
        instanceRepository.deleteById(id);
    }

    public InstanceDto updateById(String id, InstanceDto instanceDto) {
        return create(instanceDto);
    }

    public InstanceDto create(InstanceDto instanceDto) {
        Instance instance = convertToEntity(instanceDto);

        instance.state = InstanceState.STOPPED;

        instanceDto = convertToDto(instance);
        instanceDto.setConfig(configService.findById(instanceDto.getConfig().getId()));

        if(arePortsInUse(instanceDto)){

            int tcpPort = instanceDto.getConfig().getConfigurationJson().getTcpPort();
            int udpPort = instanceDto.getConfig().getConfigurationJson().getUdpPort();

            throw new ConflictException("Ports '" + tcpPort + "/tcp' and '" + udpPort + "/udp' are already in use by another instance.");
        }

        try{
            fileSystemService.createInstanceFolder(instanceDto);
            containerService.deployInstance(instanceDto);
        } catch (Exception e){
            deleteById(instanceDto.getId());
            throw e;
        }

        return save(instanceDto);
    }

    public InstanceDto save(InstanceDto instanceDto){
        Instance instance = convertToEntity(instanceDto);

        instanceRepository.save(instance);

        return convertToDto(instance);
    }

    public void startInstance(String instanceId){
        InstanceDto instanceDto = findById(instanceId);

        if(instanceDto.getState() == InstanceState.RUNNING || instanceDto.getState() == InstanceState.PAUSED){
            throw new IllegalInstanceStateException("start", instanceId, instanceDto.getState());
        }

        containerService.startInstance(instanceDto);
        instanceDto.setState(InstanceState.RUNNING);

        save(instanceDto);
    }

    public void stopInstance(String instanceId){
        InstanceDto instanceDto = findById(instanceId);

        if(instanceDto.getState() != InstanceState.RUNNING){
            throw new IllegalInstanceStateException("stop", instanceId, instanceDto.getState());
        }

        containerService.stopInstance(instanceDto);
        instanceDto.setState(InstanceState.STOPPED);

        save(instanceDto);
    }

    public void pauseInstance(String instanceId){
        InstanceDto instanceDto = findById(instanceId);

        if(instanceDto.getState() != InstanceState.RUNNING){
            throw new IllegalInstanceStateException("pause", instanceId, instanceDto.getState());
        }

        containerService.pauseInstance(instanceDto);
        instanceDto.setState(InstanceState.PAUSED);

        save(instanceDto);
    }

    public void resumeInstance(String instanceId){
        InstanceDto instanceDto = findById(instanceId);

        if(instanceDto.getState() != InstanceState.PAUSED){
            throw new IllegalInstanceStateException("resume", instanceId, instanceDto.getState());
        }

        containerService.resumeInstance(instanceDto);
        instanceDto.setState(InstanceState.PAUSED);

        save(instanceDto);
    }

    public int getActiveInstanceCount(){
        return instanceRepository.findAllByState(InstanceState.RUNNING).orElseThrow(NotFoundException::new).size();
    }

    public boolean isConfigInUse(String configId){
        return !instanceRepository.findAllByConfig_Id(configId).get().isEmpty();
    }

    private boolean arePortsInUse(InstanceDto instanceDto){

        int tcpPort = instanceDto.getConfig().getConfigurationJson().getTcpPort();
        int udpPort = instanceDto.getConfig().getConfigurationJson().getUdpPort();

        List<InstanceDto> instances = findAll();

        if(instances.isEmpty()){
            return false;
        }

        return instances.stream().anyMatch(i -> (
                        i.getConfig().getConfigurationJson().getTcpPort() == tcpPort ||
                        i.getConfig().getConfigurationJson().getUdpPort() == udpPort
        ));
    }

    public String getJsonSchema() {
        String schema = jsonSchemaService.getJsonSchema(InstanceDto.class);
        if (schema == null) {
            throw new NullPointerException("Error parsing JSON schema");
        }
        return schema;
    }

    private InstanceDto convertToDto(Instance instance) {
        return mapper.map(instance, InstanceDto.class);
    }

    private List<InstanceDto> convertToDto(List<Instance> instanceDtos){
        return instanceDtos.stream().map(instance -> mapper.map(instance, InstanceDto.class)).collect(Collectors.toList());
    }

    private Instance convertToEntity(InstanceDto instanceDto) {
        return mapper.map(instanceDto, Instance.class);
    }

    private List<Instance> convertToEntity(List<Instance> instances) {
        return instances.stream().map(instanceDto -> mapper.map(instanceDto, Instance.class)).collect(Collectors.toList());
    }
}
