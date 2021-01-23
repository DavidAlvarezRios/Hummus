package ub.dalvarezrios.hummus.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ub.dalvarezrios.hummus.models.service.IUserService;
import ub.dalvarezrios.hummus.models.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<EmailConstraint, String> {

    @Autowired
    IUserService userService;

    protected final Log _logger = LogFactory.getLog(this.getClass());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        _logger.info("hola");
        return value != null && !userService.existsUsername(value);
    }
}
