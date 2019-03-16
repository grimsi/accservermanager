package grimsi.accservermanager.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CouldNotCreateContainerException extends RuntimeException {

    public CouldNotCreateContainerException(String reason){
        super(reason);
    }
}
