package com.dominik.backend.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by dominik on 20.06.2017.
 */

@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = ValidDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidDate {

    String message() default "Data rozpoczęcia wydarzenia nie może być wcześniejsza niż data utworzenia";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
