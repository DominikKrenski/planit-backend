package com.dominik.backend.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Created by dominik on 07.04.17.
 */
public class ValidYearValidator implements ConstraintValidator<ValidYear, Integer> {

    private int currentYear;

    @Override
    public void initialize(final ValidYear constraintAnnotation) {
        currentYear = LocalDate.now().getYear();
    }

    @Override
    public boolean isValid(final Integer startYear, final ConstraintValidatorContext context) {
        if (currentYear < startYear)
            return false;

        return true;
    }
}
