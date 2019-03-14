package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.ConfigDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface ConfigsApi {

    @RequestMapping(value = "/configs",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ConfigDto> createConfig(@Valid @RequestBody ConfigDto body);

    @RequestMapping(value = "/configs/{configId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteConfigById(@PathVariable("configId") String configId);

    @RequestMapping(value = "/configs/{configId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<ConfigDto> getConfigById(@PathVariable("configId") String configId);

    @RequestMapping(value = "/configs",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<ConfigDto>> listConfigs(@Valid @RequestParam(value = "name", required = false) String name);


    @RequestMapping(value = "/configs/{configId}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<ConfigDto> updateConfigById(@Valid @RequestBody ConfigDto body, @PathVariable("configId") String configId);

    @RequestMapping(value = "/configs/schema",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<String> getConfigSchema();
}
