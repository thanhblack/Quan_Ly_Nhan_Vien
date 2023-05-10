package com.vti.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Documented
@Target({FIELD,PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameNotExistsValidator.class)
public @interface UserNameNotExists {
    String message() default "UserName da ton tai";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
