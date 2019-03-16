package grimsi.accservermanager.backend.exception;

import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IllegalInstanceStateException extends RuntimeException {

    public IllegalInstanceStateException(String action, String instanceId, InstanceState state) {
        super("Cant " + action + " instance '" + instanceId + "' because its current state is '" + state + "'");
    }
}
