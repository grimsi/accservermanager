package grimsi.accservermanager.backend.annotation;

import grimsi.accservermanager.backend.validator.AccVersionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccVersionValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccVersion {
    String message() default "unsupported ACC server version.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
