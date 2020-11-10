package grimsi.accservermanager.backend.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
public class ApiError {

    private final ZonedDateTime timestamp = ZonedDateTime.now();

    private final String error;

    private final int status;

    private final Object errors;

    private final String message;

    private final String path;

    public ApiError(HttpStatus status, Object errors, String message, String path) {
        this.status = status.value();
        error = status.getReasonPhrase();
        this.errors = errors;
        this.message = message;
        this.path = path;
    }

}
