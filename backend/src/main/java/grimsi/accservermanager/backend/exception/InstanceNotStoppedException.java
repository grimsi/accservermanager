package grimsi.accservermanager.backend.exception;

import grimsi.accservermanager.backend.enums.InstanceState;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InstanceNotStoppedException extends RuntimeException {

    public InstanceNotStoppedException(String instanceId, InstanceState state) {
        super("Cant delete instance '" + instanceId + "' because its current state is '" + state + "'");
    }
}
