package grimsi.accservermanager.backend.controller;

import grimsi.accservermanager.backend.api.LoginApi;
import grimsi.accservermanager.backend.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import grimsi.accservermanager.backend.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@RestController
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

    public ResponseEntity<String> auth(@ApiParam(value = "A JSON object containing user credentials" ,required=true )  @Valid @RequestBody UserDto body) {
        String accept = request.getHeader("Accept");
        if(userService.authenticate(body)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
