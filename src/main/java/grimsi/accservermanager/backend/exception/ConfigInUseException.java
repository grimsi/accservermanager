package grimsi.accservermanager.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConfigInUseException extends RuntimeException {
    public ConfigInUseException(String configId) {
        super("config with id '" + configId + "' is still used by at least one instance.");
    }
}
