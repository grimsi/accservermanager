package grimsi.accservermanager.backend.validator;

import grimsi.accservermanager.backend.annotation.ValidConfigId;
import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.exception.NotFoundException;
import grimsi.accservermanager.backend.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfigIdValidator implements ConstraintValidator<ValidConfigId, ConfigDto> {

    private final ConfigService configService;

    @Autowired
    public ConfigIdValidator(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public boolean isValid(ConfigDto config, ConstraintValidatorContext cxt) {
        try {
            configService.findById(config.getId());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
