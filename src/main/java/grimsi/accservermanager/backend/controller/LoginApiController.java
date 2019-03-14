package grimsi.accservermanager.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import grimsi.accservermanager.backend.api.LoginApi;
import grimsi.accservermanager.backend.dto.UserDto;
import grimsi.accservermanager.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginApiController implements LoginApi {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    public LoginApiController(ObjectMapper objectMapper,
                              HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<String> auth(@Valid @RequestBody UserDto body) {
        String accept = request.getHeader("Accept");
        if (userService.authenticate(body)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
