package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/v1/events")
public interface EventsApi {

    @GetMapping(produces = {"application/json"})
    ResponseEntity<List<EventDto>> getEvents(@Valid @RequestParam(value = "name", required = false) String name);

    @GetMapping(value = "/{eventId}", produces = {"application/json"})
    ResponseEntity<EventDto> getEventById(@PathVariable("eventId") String eventId);

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<EventDto> createEvent(@Valid @RequestBody EventDto body);

    @PutMapping(value = "/{eventId}", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<EventDto> updateEventById(@Valid @RequestBody EventDto body, @PathVariable("eventId") String eventId);

    @DeleteMapping(value = "/{eventId}", produces = {"application/json"})
    ResponseEntity<Void> deleteEventById(@PathVariable("eventId") String eventId);

    @GetMapping(value = "/stream")
    SseEmitter getEventStream();

    @GetMapping(value = "/schema", produces = {"application/json"})
    ResponseEntity<String> getEventSchema();
}
