package grimsi.accservermanager.backend.controller;

import grimsi.accservermanager.backend.api.EventApi;
import grimsi.accservermanager.backend.dto.EventDto;
import grimsi.accservermanager.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class EventApiController implements EventApi {

    private final HttpServletRequest request;
    private final EventService eventService;

    @Autowired
    public EventApiController(HttpServletRequest request,
                              EventService eventService) {
        this.request = request;
        this.eventService = eventService;
    }

    @Override
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto body) {
        String accept = request.getHeader("Accept");
        EventDto eventDto = eventService.create(body);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteEventById(@PathVariable("eventId") String eventId) {
        String accept = request.getHeader("Accept");
        eventService.deleteById(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<EventDto> getEventById(@PathVariable("eventId") String eventId) {
        String accept = request.getHeader("Accept");
        EventDto eventDto = eventService.findById(eventId);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<EventDto>> listEvents(@Valid @RequestParam(value = "name", required = false) String name) {
        String accept = request.getHeader("Accept");
        List<EventDto> eventDtos = eventService.findAll();
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EventDto> updateEventById(@Valid @RequestBody EventDto body, @PathVariable("eventId") String eventId) {
        String accept = request.getHeader("Accept");
        EventDto eventDto = eventService.updateById(eventId, body);
        return new ResponseEntity<>(eventDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getEventSchema() {
        String accept = request.getHeader("Accept");
        String schema = eventService.getJsonSchema();
        return new ResponseEntity<>(schema, HttpStatus.OK);
    }

}
