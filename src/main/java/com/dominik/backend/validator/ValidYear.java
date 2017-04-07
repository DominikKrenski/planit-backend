package com.dominik.backend.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by dominik on 07.04.17.
 */

@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = ValidYearValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidYear {

    String message() default "Rok rozpoczęcia studiów nie może być późniejszy niż rok obecny";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
