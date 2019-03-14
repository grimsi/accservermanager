package grimsi.accservermanager.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grimsi.accservermanager.backend.api.ConfigsApi;
import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.service.ConfigService;
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

    @Override
    public ResponseEntity<ConfigDto> createConfig(@Valid @RequestBody ConfigDto body) {
        String accept = request.getHeader("Accept");
        ConfigDto configDto = configService.create(body);
        return new ResponseEntity<>(configDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteConfigById(@PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        configService.deleteById(configId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ConfigDto> getConfigById(@PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        ConfigDto configDto = configService.findById(configId);
        return new ResponseEntity<>(configDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ConfigDto>> listConfigs(@Valid @RequestParam(value = "name", required = false) String name) {
        String accept = request.getHeader("Accept");
        List<ConfigDto> configDtos = configService.findAll();
        return new ResponseEntity<>(configDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ConfigDto> updateConfigById(@Valid @RequestBody ConfigDto body, @PathVariable("configId") String configId) {
        String accept = request.getHeader("Accept");
        ConfigDto configDto = configService.updateById(configId, body);
        return new ResponseEntity<>(configDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getConfigSchema() {
        String accept = request.getHeader("Accept");
        String schema = configService.getJsonSchema();
        return new ResponseEntity<>(schema, HttpStatus.OK);
    }

}
