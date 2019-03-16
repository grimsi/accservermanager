package grimsi.accservermanager.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnknownHostOsException extends RuntimeException{

    public UnknownHostOsException(){
        super("Could not detect host operating system");
    }
}
