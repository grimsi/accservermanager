package grimsi.accservermanager.backend.controller;

import grimsi.accservermanager.backend.api.LoginApi;
import grimsi.accservermanager.backend.dto.UserDto;
import grimsi.accservermanager.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class LoginApiController implements LoginApi {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity auth(UserDto body) {
        return userService.authenticate(body) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
