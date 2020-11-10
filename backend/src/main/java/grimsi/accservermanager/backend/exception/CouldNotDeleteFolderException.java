package grimsi.accservermanager.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.Path;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CouldNotDeleteFolderException extends RuntimeException {
    public CouldNotDeleteFolderException(Path folderPath) {
        super("Could not delete folder '" + folderPath + "'.");
    }
}
