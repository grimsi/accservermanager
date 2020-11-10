package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/v1/login")
public interface LoginApi {

    @PostMapping(produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity auth(@Valid @RequestBody UserDto body);

}
