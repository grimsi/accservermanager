package grimsi.accservermanager.backend.exception;

import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CouldNotStartInstanceException extends RuntimeException {

    public CouldNotStartInstanceException(String instanceId, InstanceState state) {
        super("Cant start instance '" + instanceId + "' because its current state is '" + state + "'");
    }
}
