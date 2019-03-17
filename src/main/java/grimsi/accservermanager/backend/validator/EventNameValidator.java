package grimsi.accservermanager.backend.validator;

import grimsi.accservermanager.backend.annotation.UniqueEventName;
import grimsi.accservermanager.backend.exception.NotFoundException;
import grimsi.accservermanager.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EventNameValidator implements ConstraintValidator<UniqueEventName, String> {

    @Autowired
    EventService eventService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt) {
        try {
            eventService.findByName(name);
            return false;
        } catch (NotFoundException e) {
            return true;
        }
    }
}
