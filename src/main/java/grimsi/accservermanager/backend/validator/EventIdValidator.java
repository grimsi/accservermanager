package grimsi.accservermanager.backend.validator;

import grimsi.accservermanager.backend.annotation.ValidEventId;
import grimsi.accservermanager.backend.dto.EventDto;
import grimsi.accservermanager.backend.exception.NotFoundException;
import grimsi.accservermanager.backend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EventIdValidator implements ConstraintValidator<ValidEventId, EventDto> {

    private final EventService eventService;

    @Autowired
    public EventIdValidator(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public boolean isValid(EventDto event, ConstraintValidatorContext cxt) {
        try {
            eventService.findById(event.getId());
            return true;
        } catch (NotFoundException e) {
            return false;
        }
    }
}
