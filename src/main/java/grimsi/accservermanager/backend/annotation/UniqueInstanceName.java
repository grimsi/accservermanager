package grimsi.accservermanager.backend.annotation;

import grimsi.accservermanager.backend.validator.InstanceNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InstanceNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueInstanceName {
    String message() default "instance name has to be unique.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
