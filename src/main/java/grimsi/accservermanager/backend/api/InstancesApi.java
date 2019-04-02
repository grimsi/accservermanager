package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v1/instances")
public interface InstancesApi {

    @GetMapping(produces = {"application/json"})
    ResponseEntity<List<InstanceDto>> getInstances(@Valid @RequestParam(value = "name", required = false) String name, @Valid @RequestParam(value = "state", required = false) InstanceState state);

    @GetMapping(value = "/{instanceId}", produces = {"application/json"})
    ResponseEntity<InstanceDto> getInstanceById(@PathVariable("instanceId") String instanceId);

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<InstanceDto> createInstance(@Valid @RequestBody InstanceDto body);

    @PutMapping(value = "/{instanceId}", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<InstanceDto> updateInstanceById(@Valid @RequestBody InstanceDto body, @PathVariable("instanceId") String instanceId);

    @DeleteMapping(value = "/{instanceId}", produces = {"application/json"})
    ResponseEntity<Void> deleteInstanceById(@PathVariable("instanceId") String instanceId);

    @GetMapping(value = "/{instanceId}/start", produces = {"application/json"})
    ResponseEntity<Void> startInstanceById(@PathVariable("instanceId") String instanceId);

    @GetMapping(value = "/{instanceId}/stop", produces = {"application/json"})
    ResponseEntity<Void> stopInstanceById(@PathVariable("instanceId") String instanceId);

    @GetMapping(value = "/{instanceId}/pause", produces = {"application/json"})
    ResponseEntity<Void> pauseInstanceById(@PathVariable("instanceId") String instanceId);

    @GetMapping(value = "/{instanceId}/resume", produces = {"application/json"})
    ResponseEntity<Void> resumeInstanceById(@PathVariable("instanceId") String instanceId);

    @GetMapping(value = "/stream")
    SseEmitter getInstanceStream();

    @GetMapping(value = "/schema", produces = {"application/json"})
    ResponseEntity<String> getInstanceSchema();

}
