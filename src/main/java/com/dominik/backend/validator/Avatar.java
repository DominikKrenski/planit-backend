package com.dominik.backend.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dominik on 07.06.17.
 */

@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = AvatarValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Avatar {

    String message() default "Avatar może mieć jedynie format .jpg, .jpeg, .ico lub .png";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
