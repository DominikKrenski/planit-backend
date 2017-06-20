package com.dominik.backend.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by dominik on 20.06.2017.
 */

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidHoursValidator.class)
@Documented
public @interface ValidHours {

    String message() default "Godzina rozpoczęcia jest wcześniejsza niż godzina zakończenia";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String first();
    String second();

    @Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ValidHours[] value();
    }
}
