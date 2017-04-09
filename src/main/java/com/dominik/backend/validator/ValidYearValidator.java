package com.dominik.backend.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Created by dominik on 07.04.17.
 */
public class ValidYearValidator implements ConstraintValidator<ValidYear, Integer> {

    private final Logger logger = LoggerFactory.getLogger(ValidYearValidator.class);
    private int currentYear;

    @Override
    public void initialize(final ValidYear constraintAnnotation) {

        logger.info("INICJALIZACJA WALIDATORA VALID_YEAR_VALIDATOR");

        currentYear = LocalDate.now().getYear();
    }

    @Override
    public boolean isValid(final Integer startYear, final ConstraintValidatorContext context) {
        if (currentYear < startYear)
            return false;

        return true;
    }
}
