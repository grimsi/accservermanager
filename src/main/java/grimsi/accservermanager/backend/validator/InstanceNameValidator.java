package grimsi.accservermanager.backend.validator;

import grimsi.accservermanager.backend.annotation.UniqueInstanceName;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class InstanceNameValidator implements ConstraintValidator<UniqueInstanceName, String> {

    @Autowired
    InstanceService instanceService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext cxt) {
        List<InstanceDto> instances = instanceService.findByName(name);
        return instances.parallelStream().noneMatch(instance -> instance.getName().toLowerCase().equals(name.toLowerCase()));
    }
}
