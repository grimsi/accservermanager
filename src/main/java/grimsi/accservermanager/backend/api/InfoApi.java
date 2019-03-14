package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.SystemInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface InfoApi {

    @RequestMapping(value = "/info",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<SystemInfoDto> getInfo();

}
