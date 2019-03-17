package grimsi.accservermanager.backend.annotation;

import grimsi.accservermanager.backend.validator.EventIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EventIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEventId {
    String message() default "configuration id is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
