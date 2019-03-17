package grimsi.accservermanager.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EventInUseException extends RuntimeException {
    private static final long serialVersionUID = -3073532927650758292L;

    public EventInUseException(String configId) {
        super("configuration with id '" + configId + "' is still used by at least one instance.");
    }
}
