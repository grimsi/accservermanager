package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface EventApi {

    @RequestMapping(value = "/events",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto body);

    @RequestMapping(value = "/events/{eventId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteEventById(@PathVariable("eventId") String eventId);

    @RequestMapping(value = "/events/{eventId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<EventDto> getEventById(@PathVariable("eventId") String eventId);

    @RequestMapping(value = "/events",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<EventDto>> listEvents(@Valid @RequestParam(value = "name", required = false) String name);


    @RequestMapping(value = "/events/{eventId}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<EventDto> updateEventById(@Valid @RequestBody EventDto body, @PathVariable("eventId") String eventId);

    @RequestMapping(value = "/events/schema",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<String> getEventSchema();
}
