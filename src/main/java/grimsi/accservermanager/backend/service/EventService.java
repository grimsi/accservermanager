package grimsi.accservermanager.backend.service;

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

import java.util.List;

@Service
public class EventService {

    @Autowired
    JsonSchemaService jsonSchemaService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    InstanceService instanceService;

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

    public void deleteById(String id) {
        if (instanceService.isEventInUse(id)) {
            throw new EventInUseException(id);
        }

        findById(id);

        eventRepository.deleteById(id);
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

        return save(eventDto);
    }

    public EventDto create(EventDto eventDto) {
        return save(eventDto);
    }

    @SuppressWarnings("Duplicates")
    public EventDto save(EventDto eventDto) {
        Event event = convertToEntity(eventDto);

        try {
            event = eventRepository.save(event);
        } catch (DuplicateKeyException e) {
            throw new ConflictException("Name '" + event.name + "' is already in use.");
        }

        return convertToDto(event);
    }

    public String getJsonSchema() {
        String schema = jsonSchemaService.getJsonSchema(EventDto.class);
        if (schema == null) {
            throw new NullPointerException("Error parsing JSON schema");
        }
        return schema;
    }

    private EventDto convertToDto(Event event) {
        return mapper.map(event, EventDto.class);
    }

    private Event convertToEntity(EventDto eventDto) {
        return mapper.map(eventDto, Event.class);
    }
}
