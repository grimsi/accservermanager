package grimsi.accservermanager.backend.controller;

import grimsi.accservermanager.backend.api.EventsApi;
import grimsi.accservermanager.backend.dto.EventDto;
import grimsi.accservermanager.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EventsApiController implements EventsApi {

    @Autowired
    private EventService eventService;

    @Override
    public ResponseEntity<List<EventDto>> getEvents(String name) {
        List<EventDto> eventDtos = eventService.findAll();
        return ResponseEntity.ok(eventDtos);
    }

    @Override
    public ResponseEntity<EventDto> getEventById(String eventId) {
        EventDto eventDto = eventService.findById(eventId);
        return ResponseEntity.ok(eventDto);
    }

    @Override
    public ResponseEntity<EventDto> createEvent(@Valid EventDto body) {
        EventDto eventDto = eventService.create(body);
        return ResponseEntity.ok(eventDto);
    }

    @Override
    public ResponseEntity<EventDto> updateEventById(EventDto body, String eventId) {
        EventDto eventDto = eventService.updateById(eventId, body);
        return ResponseEntity.ok(eventDto);
    }

    @Override
    public ResponseEntity<Void> deleteEventById(String eventId) {
        eventService.deleteById(eventId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public SseEmitter getEventStream() {
        return eventService.createNewEventEmitter();
    }

    @Override
    public ResponseEntity<String> getEventSchema() {
        String schema = eventService.getJsonSchema();
        return ResponseEntity.ok(schema);
    }

}
