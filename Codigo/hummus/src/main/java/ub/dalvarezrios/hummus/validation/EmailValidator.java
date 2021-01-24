package ub.dalvarezrios.hummus.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.service.IUserService;

@Component
public class EmailValidator implements Validator {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.not.empty");
        if(userService.existsEmail(user.getEmail())){
            errors.rejectValue("email", "email.does.exists");
        }

    }
}
