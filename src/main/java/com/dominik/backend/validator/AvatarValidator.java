package com.dominik.backend.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by dominik on 07.06.17.
 */
public class AvatarValidator implements ConstraintValidator<Avatar, String> {

    private final Logger logger = LoggerFactory.getLogger(AvatarValidator.class);

    @Override
    public void initialize(final Avatar constraintAnnotation) {
        logger.info("INICJALIZACJA WALIDATORA AVATAR_VALIDATOR");
    }

    @Override
    public boolean isValid(final String avatar, final ConstraintValidatorContext context) {

        String[] substring = avatar.split(";");

        // Obrazy zakodowane z wykorzystaniem Base64 mają postać data:image/jpeg;base64,lsdjdkafaldkfal...
        // Sprawdzam więc czy początek ma odpowiednią postać

        if (substring[0].equals("data:image/jpeg") || substring[0].equals("data:image/jpg") || substring[0].equals("data:image/ico") ||
                substring[0].equals("data:image/png"))
            return true;

        return false;
    }
}
