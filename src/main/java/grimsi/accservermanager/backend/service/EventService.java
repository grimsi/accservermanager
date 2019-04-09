package grimsi.accservermanager.backend.service;

import com.google.gson.Gson;
import grimsi.accservermanager.backend.dto.EventDto;
import grimsi.accservermanager.backend.entity.Event;
import grimsi.accservermanager.backend.exception.ConflictException;
import grimsi.accservermanager.backend.exception.EventInUseException;
import grimsi.accservermanager.backend.exception.NotFoundException;
import grimsi.accservermanager.backend.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    JsonSchemaService jsonSchemaService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    InstanceService instanceService;

    @Autowired
    ModelMapper mapper;

    @Autowired
    Gson gson;


    private List<SseEmitter> sseEmitters = new ArrayList<>();

    public List<EventDto> findAll() {
        List<Event> configs = eventRepository.findAll();
        return mapper.map(configs, new TypeToken<List<EventDto>>() {
        }.getType());
    }

    public EventDto findById(String id) {
        Event event = eventRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertToDto(event);
    }

    public EventDto findByName(String name) {
        Event event = eventRepository.findByName(name).orElseThrow(NotFoundException::new);
        return convertToDto(event);
    }

    public EventDto create(EventDto eventDto) {
        eventDto = save(eventDto);
        emitNewEvent("create", gson.toJson(eventDto));
        return eventDto;
    }

    public EventDto updateById(String id, EventDto eventDto) {
        findById(id);

        /* Set the values that the user is not allowed to change */
        eventDto.setId(id);

        /* Set the restart required flag for all instances that use this event */
        instanceService.findInstancesByEventId(id).forEach(i -> {
            i.setRestartRequired(true);
            instanceService.updateById(i.getId(), i);
        });

        eventDto = save(eventDto);
        emitNewEvent("update", gson.toJson(eventDto));
        return eventDto;
    }

    public void deleteById(String id) {
        findById(id);

        if (instanceService.isEventInUse(id)) {
            throw new EventInUseException(id);
        }

        eventRepository.deleteById(id);
        emitNewEvent("delete", id);
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
        String schema = jsonSchemaService.getJsonSchema(EventDto.class);
        if (schema == null) {
            throw new NullPointerException("Error parsing JSON schema");
        }
        return schema;
    }

    @SuppressWarnings("Duplicates")
    public EventDto save(EventDto eventDto) {
        Event event = convertToEntity(eventDto);

        try {
            event = eventRepository.save(event);
        } catch (DuplicateKeyException e) {
            throw new ConflictException("Name '" + event.getName() + "' is already in use.");
        }

        return convertToDto(event);
    }

    private EventDto convertToDto(Event event) {
        return mapper.map(event, EventDto.class);
    }

    private Event convertToEntity(EventDto eventDto) {
        return mapper.map(eventDto, Event.class);
    }
}
