package grimsi.accservermanager.backend.validator;

import grimsi.accservermanager.backend.annotation.ValidAccVersion;
import grimsi.accservermanager.backend.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;

public class AccVersionValidator implements ConstraintValidator<ValidAccVersion, String> {

    private final FileSystemService fileSystemService;

    @Autowired
    public AccVersionValidator(FileSystemService fileSystemService){
        this.fileSystemService = fileSystemService;
    }

    @Override
    public boolean isValid(String version, ConstraintValidatorContext cxt) {
        return new HashSet<>(fileSystemService.getInstalledServerVersions()).contains(version);
    }
}
