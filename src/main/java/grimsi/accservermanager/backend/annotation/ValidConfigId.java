package grimsi.accservermanager.backend.annotation;

import grimsi.accservermanager.backend.validator.ConfigIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConfigIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidConfigId {
    String message() default "config id is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
