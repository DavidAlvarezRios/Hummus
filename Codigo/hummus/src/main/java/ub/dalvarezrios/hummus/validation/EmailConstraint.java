package ub.dalvarezrios.hummus.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface EmailConstraint {

    String message() default "{EmailConstraint.user.email}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
