package ub.dalvarezrios.hummus.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface UsernameConstraint {

    String message() default "{UsernameConstraint.user.username}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
