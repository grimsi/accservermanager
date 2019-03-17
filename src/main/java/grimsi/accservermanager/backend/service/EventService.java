package grimsi.accservermanager.backend.service;

import grimsi.accservermanager.backend.dto.EventDto;
import grimsi.accservermanager.backend.entity.Event;
import grimsi.accservermanager.backend.exception.EventInUseException;
import grimsi.accservermanager.backend.exception.NotFoundException;
import grimsi.accservermanager.backend.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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
        return create(eventDto);
    }

    public EventDto create(EventDto eventDto) {
        Event event = convertToEntity(eventDto);
        event = eventRepository.save(event);
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
