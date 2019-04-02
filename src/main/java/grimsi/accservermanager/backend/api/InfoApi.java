package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.SystemInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/info")
public interface InfoApi {

    @GetMapping(produces = {"application/json"})
    ResponseEntity<SystemInfoDto> getInfo();

}
