package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface InstancesApi {

    @RequestMapping(value = "/instances",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<InstanceDto> createInstance(@Valid @RequestBody InstanceDto body);

    @RequestMapping(value = "/instances/{instanceId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteInstanceById(@PathVariable("instanceId") String instanceId);

    @RequestMapping(value = "/instances/{instanceId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<InstanceDto> getInstanceById(@PathVariable("instanceId") String instanceId);

    @RequestMapping(value = "/instances",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<InstanceDto>> listInstances(@Valid @RequestParam(value = "name", required = false) String name, @Valid @RequestParam(value = "state", required = false) InstanceState state);

    @RequestMapping(value = "/instances/{instanceId}/start",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Void> startInstanceById(@PathVariable("instanceId") String instanceId);

    @RequestMapping(value = "/instances/{instanceId}/stop",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Void> stopInstanceById(@PathVariable("instanceId") String instanceId);

    @RequestMapping(value = "/instances/{instanceId}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<InstanceDto> updateInstanceById(@Valid @RequestBody InstanceDto body, @PathVariable("instanceId") String instanceId);

    @RequestMapping(value = "/instances/schema",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<String> getInstanceSchema();

}
