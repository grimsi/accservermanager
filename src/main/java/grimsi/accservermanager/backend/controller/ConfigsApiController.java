package grimsi.accservermanager.backend.controller;

import grimsi.accservermanager.backend.api.ConfigsApi;
import grimsi.accservermanager.backend.dto.ConfigDto;
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
public class ConfigsApiController implements ConfigsApi {

    private static final Logger log = LoggerFactory.getLogger(ConfigsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ConfigsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ConfigDto> createConfig(@ApiParam(value = "A JSON object containing the configuration" ,required=true )  @Valid @RequestBody ConfigDto body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ConfigDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteConfigById(@ApiParam(value = "The id of the configuration to delete",required=true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ConfigDto> getConfigById(@ApiParam(value = "The id of the configuration to retrieve",required=true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ConfigDto>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ArrayList<ConfigDto>> listConfigs(@ApiParam(value = "Filter by name") @Valid @RequestParam(value = "name", required = false) String name) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ArrayList<ConfigDto>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ConfigDto> updateConfigById(@ApiParam(value = "A JSON object containing the configuration" ,required=true )  @Valid @RequestBody ConfigDto body, @ApiParam(value = "The id of the configuration to retrieve",required=true) @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<ConfigDto>(HttpStatus.NOT_IMPLEMENTED);
    }

}
