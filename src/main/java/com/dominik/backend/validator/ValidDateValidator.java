package com.dominik.backend.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by dominik on 20.06.2017.
 */

public class ValidDateValidator implements ConstraintValidator<ValidDate, LocalDate> {

    private final Logger logger = LoggerFactory.getLogger(ValidDateValidator.class);
    private LocalDate currentDate;

    @Override
    public void initialize(final ValidDate constraintAnnotation) {

        logger.info("INICJALIZACJA WALIDATORA VALID_DATE_VALIDATOR");

        currentDate = LocalDate.now();
    }

    @Override
    public boolean isValid(final LocalDate startDate, final ConstraintValidatorContext context) {
        if (startDate.isBefore(currentDate))
            return false;

        return true;
    }
}
