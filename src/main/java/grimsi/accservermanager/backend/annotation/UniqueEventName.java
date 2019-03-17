package grimsi.accservermanager.backend.annotation;

import grimsi.accservermanager.backend.validator.EventNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EventNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEventName {
    String message() default "event name has to be unique.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
