package grimsi.accservermanager.backend.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception{
    private HttpStatus status;
    public ApiException (HttpStatus status, String msg) {
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
