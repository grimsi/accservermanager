package grimsi.accservermanager.backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ContainerException extends RuntimeException{

    public ContainerException(String message){
        super(message);
    }
}
