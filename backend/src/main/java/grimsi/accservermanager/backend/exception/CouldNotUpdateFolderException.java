package grimsi.accservermanager.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.Path;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CouldNotUpdateFolderException extends RuntimeException {
    private static final long serialVersionUID = 5396484203660186517L;

    public CouldNotUpdateFolderException(Path folderPath) {
        super("Could not update folder '" + folderPath + "'.");
    }
}
