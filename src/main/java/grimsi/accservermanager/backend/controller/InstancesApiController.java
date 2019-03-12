package grimsi.accservermanager.backend.controller;

import grimsi.accservermanager.backend.api.InstancesApi;
import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@javax.annotation.Generated(value = "grimsi.accservermanager.backend.codegen.v3.generators.java.SpringCodegen", date = "2019-03-10T17:37:16.729Z[GMT]")
@Controller
public class InstancesApiController implements InstancesApi {

    private static final Logger log = LoggerFactory.getLogger(InstancesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public InstancesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ConfigDto> createInstance(@ApiParam(value = "A JSON object containing the instance" ,required=true )  @Valid @RequestBody InstanceDto body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ConfigDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteInstanceById(@ApiParam(value = "The id of the instance to delete",required=true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InstanceDto> getInstanceById(@ApiParam(value = "The id of the instance to retrieve",required=true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<InstanceDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ArrayList<InstanceDto>> listInstances(@ApiParam(value = "Filter by name") @Valid @RequestParam(value = "name", required = false) String name, @ApiParam(value = "") @Valid @RequestParam(value = "state", required = false) InstanceState state) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ArrayList<InstanceDto>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> startInstanceById(@ApiParam(value = "The id of the instance to start",required=true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> stopInstanceById(@ApiParam(value = "The id of the instance to stop",required=true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InstanceDto> updateInstanceById(@ApiParam(value = "A JSON object containing the instance" ,required=true )  @Valid @RequestBody InstanceDto body, @ApiParam(value = "The id of the instance to retrieve",required=true) @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<InstanceDto>(HttpStatus.NOT_IMPLEMENTED);
    }

}
