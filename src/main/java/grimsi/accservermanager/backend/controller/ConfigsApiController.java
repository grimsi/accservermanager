package grimsi.accservermanager.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grimsi.accservermanager.backend.api.ConfigsApi;
import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.service.ConfigService;
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
public class ConfigsApiController implements ConfigsApi {

    private static final Logger log = LoggerFactory.getLogger(ConfigsApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final ConfigService configService;

    @Autowired
    public ConfigsApiController(ObjectMapper objectMapper,
                                HttpServletRequest request,
                                ConfigService configService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.configService = configService;
    }

    public ResponseEntity<ConfigDto> createConfig(@ApiParam(value = "A JSON object containing the configuration", required = true) @Valid @RequestBody ConfigDto body) {
        String accept = request.getHeader("Accept");
        ConfigDto configDto = configService.create(body);
        return new ResponseEntity<ConfigDto>(configDto, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteConfigById(@ApiParam(value = "The id of the configuration to delete", required = true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        configService.deleteById(configId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ConfigDto> getConfigById(@ApiParam(value = "The id of the configuration to retrieve", required = true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        ConfigDto configDto = configService.findById(configId);
        return new ResponseEntity<>(configDto, HttpStatus.OK);
    }

    public ResponseEntity<List<ConfigDto>> listConfigs(@ApiParam(value = "Filter by name") @Valid @RequestParam(value = "name", required = false) String name) {
        String accept = request.getHeader("Accept");
        List<ConfigDto> configDtos = configService.findAll();
        return new ResponseEntity<>(configDtos, HttpStatus.OK);
    }

    public ResponseEntity<ConfigDto> updateConfigById(@ApiParam(value = "A JSON object containing the configuration", required = true) @Valid @RequestBody ConfigDto body, @ApiParam(value = "The id of the configuration to retrieve", required = true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        ConfigDto configDto = configService.updateById(configId, body);
        return new ResponseEntity<>(configDto, HttpStatus.OK);
    }

    public ResponseEntity<String> getConfigSchema() {
        String accept = request.getHeader("Accept");
        String schema = configService.getJsonSchema();
        return new ResponseEntity<String>(schema, HttpStatus.OK);
    }

}
