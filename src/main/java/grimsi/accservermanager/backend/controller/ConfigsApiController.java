package grimsi.accservermanager.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grimsi.accservermanager.backend.api.ConfigsApi;
import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.service.JsonSchemaService;
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
import java.util.ArrayList;

@javax.annotation.Generated(value = "grimsi.accservermanager.backend.codegen.v3.generators.java.SpringCodegen", date = "2019-03-10T17:37:16.729Z[GMT]")
@Controller
public class ConfigsApiController implements ConfigsApi {

    private static final Logger log = LoggerFactory.getLogger(ConfigsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final JsonSchemaService jsonSchemaService;

    @Autowired
    public ConfigsApiController(ObjectMapper objectMapper,
                                HttpServletRequest request,
                                JsonSchemaService jsonSchemaService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.jsonSchemaService = jsonSchemaService;
    }

    public ResponseEntity<ConfigDto> createConfig(@ApiParam(value = "A JSON object containing the configuration", required = true) @Valid @RequestBody ConfigDto body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ConfigDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteConfigById(@ApiParam(value = "The id of the configuration to delete", required = true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ConfigDto> getConfigById(@ApiParam(value = "The id of the configuration to retrieve", required = true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ConfigDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ArrayList<ConfigDto>> listConfigs(@ApiParam(value = "Filter by name") @Valid @RequestParam(value = "name", required = false) String name) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ArrayList<ConfigDto>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ConfigDto> updateConfigById(@ApiParam(value = "A JSON object containing the configuration", required = true) @Valid @RequestBody ConfigDto body, @ApiParam(value = "The id of the configuration to retrieve", required = true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ConfigDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> getConfigSchema() {
        String accept = request.getHeader("Accept");
        String schema = jsonSchemaService.getJsonSchema(ConfigDto.class);
        if (schema == null) {
            throw new NullPointerException("Error parsing JSON schema");
        }
        return new ResponseEntity<String>(schema, HttpStatus.OK);
    }

}
