package grimsi.accservermanager.backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CouldNotStartContainerException extends RuntimeException{

    public CouldNotStartContainerException(String containerId, String message){
        super("Could not start container '" + containerId + "': " + message);
    }
}
