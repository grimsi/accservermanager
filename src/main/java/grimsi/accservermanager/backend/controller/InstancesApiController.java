package grimsi.accservermanager.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grimsi.accservermanager.backend.api.InstancesApi;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import grimsi.accservermanager.backend.service.InstanceService;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@javax.annotation.Generated(value = "grimsi.accservermanager.backend.codegen.v3.generators.java.SpringCodegen", date = "2019-03-10T17:37:16.729Z[GMT]")
@Controller
public class InstancesApiController implements InstancesApi {

    private static final Logger log = LoggerFactory.getLogger(InstancesApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final InstanceService instanceService;

    @Autowired
    public InstancesApiController(ObjectMapper objectMapper,
                                  HttpServletRequest request,
                                  InstanceService instanceService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.instanceService = instanceService;
    }

    public ResponseEntity<InstanceDto> createInstance(@ApiParam(value = "A JSON object containing the instance", required = true) @Valid @RequestBody InstanceDto body) {
        String accept = request.getHeader("Accept");
        InstanceDto instanceDto = instanceService.create(body);
        return new ResponseEntity<InstanceDto>(instanceDto, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteInstanceById(@ApiParam(value = "The id of the instance to delete", required = true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        instanceService.deleteById(instanceId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<InstanceDto> getInstanceById(@ApiParam(value = "The id of the instance to retrieve", required = true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        InstanceDto instanceDto = instanceService.findById(instanceId);
        return new ResponseEntity<InstanceDto>(instanceDto, HttpStatus.OK);
    }

    public ResponseEntity<List<InstanceDto>> listInstances(@ApiParam(value = "Filter by name") @Valid @RequestParam(value = "name", required = false) String name, @ApiParam(value = "") @Valid @RequestParam(value = "state", required = false) InstanceState state) {
        String accept = request.getHeader("Accept");
        List<InstanceDto> instanceDtos = instanceService.findAll();
        return new ResponseEntity<List<InstanceDto>>(instanceDtos, HttpStatus.OK);
    }

    public ResponseEntity<Void> startInstanceById(@ApiParam(value = "The id of the instance to start", required = true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> stopInstanceById(@ApiParam(value = "The id of the instance to stop", required = true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InstanceDto> updateInstanceById(@ApiParam(value = "A JSON object containing the instance", required = true) @Valid @RequestBody InstanceDto body, @ApiParam(value = "The id of the instance to retrieve", required = true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<InstanceDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> getInstanceSchema() {
        String schema = instanceService.getJsonSchema();
        return new ResponseEntity<String>(schema, HttpStatus.OK);
    }
}
