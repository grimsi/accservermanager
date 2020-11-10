package grimsi.accservermanager.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grimsi.accservermanager.backend.api.InfoApi;
import grimsi.accservermanager.backend.dto.SystemInfoDto;
import grimsi.accservermanager.backend.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class InfoApiController implements InfoApi {

    private final HttpServletRequest request;
    @Autowired
    InfoService infoService;

    @org.springframework.beans.factory.annotation.Autowired
    public InfoApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public ResponseEntity<SystemInfoDto> getInfo() {

        SystemInfoDto systemInfo = infoService.getSystemInfo();
        return new ResponseEntity<>(systemInfo, HttpStatus.OK);
    }

}
