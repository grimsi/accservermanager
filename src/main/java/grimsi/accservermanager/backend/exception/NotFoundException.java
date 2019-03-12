package grimsi.accservermanager.backend.exception;

@javax.annotation.Generated(value = "grimsi.accservermanager.backend.codegen.v3.generators.java.SpringCodegen", date = "2019-03-10T17:37:16.729Z[GMT]")
public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
