package grimsi.accservermanager.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grimsi.accservermanager.backend.api.InstancesApi;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import grimsi.accservermanager.backend.service.InstanceService;
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

    @Override
    public ResponseEntity<InstanceDto> createInstance(@Valid @RequestBody InstanceDto body) {
        String accept = request.getHeader("Accept");
        InstanceDto instanceDto = instanceService.create(body);
        return new ResponseEntity<>(instanceDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteInstanceById(@PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        instanceService.deleteById(instanceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<InstanceDto> getInstanceById(@PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        InstanceDto instanceDto = instanceService.findById(instanceId);
        return new ResponseEntity<>(instanceDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<InstanceDto>> listInstances(@Valid @RequestParam(value = "name", required = false) String name, @Valid @RequestParam(value = "state", required = false) InstanceState state) {
        String accept = request.getHeader("Accept");
        List<InstanceDto> instanceDtos = instanceService.findAll();
        return new ResponseEntity<>(instanceDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> startInstanceById(@PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> stopInstanceById(@PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<InstanceDto> updateInstanceById(@Valid @RequestBody InstanceDto body, @PathVariable("instanceId") String instanceId) {
        String accept = request.getHeader("Accept");
        InstanceDto instanceDto = instanceService.updateById(instanceId, body);
        return new ResponseEntity<>(instanceDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getInstanceSchema() {
        String schema = instanceService.getJsonSchema();
        return new ResponseEntity<>(schema, HttpStatus.OK);
    }
}
