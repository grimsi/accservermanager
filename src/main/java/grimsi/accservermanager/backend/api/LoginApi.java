package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

public interface LoginApi {

    @RequestMapping(value = "/login",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<String> auth(@Valid @RequestBody UserDto body);

}
