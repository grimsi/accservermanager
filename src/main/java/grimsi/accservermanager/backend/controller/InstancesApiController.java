package grimsi.accservermanager.backend.controller;

import grimsi.accservermanager.backend.api.InstancesApi;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import grimsi.accservermanager.backend.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Controller
public class InstancesApiController implements InstancesApi {

    @Autowired
    private InstanceService instanceService;

    @Override
    public ResponseEntity<List<InstanceDto>> getInstances(String name, InstanceState state) {
        List<InstanceDto> instanceDtos = instanceService.findAll();
        return ResponseEntity.ok(instanceDtos);
    }

    @Override
    public ResponseEntity<InstanceDto> getInstanceById(String instanceId) {
        InstanceDto instanceDto = instanceService.findById(instanceId);
        return ResponseEntity.ok(instanceDto);
    }

    @Override
    public ResponseEntity<InstanceDto> createInstance(InstanceDto body) {
        InstanceDto instanceDto = instanceService.create(body);
        return ResponseEntity.ok(instanceDto);
    }

    @Override
    public ResponseEntity<InstanceDto> updateInstanceById(InstanceDto body, String instanceId) {
        InstanceDto instanceDto = instanceService.updateById(instanceId, body);
        return ResponseEntity.ok(instanceDto);
    }

    @Override
    public ResponseEntity<Void> deleteInstanceById(String instanceId) {
        instanceService.deleteById(instanceId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> startInstanceById(String instanceId) {
        instanceService.startInstance(instanceId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> stopInstanceById(String instanceId) {
        instanceService.stopInstance(instanceId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> pauseInstanceById(String instanceId) {
        instanceService.pauseInstance(instanceId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> resumeInstanceById(String instanceId) {
        instanceService.resumeInstance(instanceId);
        return ResponseEntity.ok().build();
    }

    @Override
    public SseEmitter getInstanceStream() {
        return instanceService.createNewEventEmitter();
        //return instanceService.createNewEventEmitter();
    }

    @Override
    public ResponseEntity<String> getInstanceSchema() {
        String schema = instanceService.getJsonSchema();
        return ResponseEntity.ok(schema);
    }
}
