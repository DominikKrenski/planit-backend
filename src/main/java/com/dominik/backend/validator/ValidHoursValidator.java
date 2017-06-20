package com.dominik.backend.validator;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

/**
 * Created by dominik on 20.06.2017.
 */


public class ValidHoursValidator implements ConstraintValidator<ValidHours, Object> {

    private final Logger logger = LoggerFactory.getLogger(ValidHoursValidator.class);

    private String startHour;
    private String endHour;

    @Override
    public void initialize(final ValidHours constraintAnnotation) {

        logger.info("INICJALIZACJA WALIDATORA VALID_HOURS_VALIDATOR");

        startHour = constraintAnnotation.first();
        endHour = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        try {
            final String firstValue = BeanUtils.getProperty(value, startHour);
            final String secondValue = BeanUtils.getProperty(value, endHour);

            LocalTime startHour = LocalTime.parse(firstValue);
            LocalTime endHour = LocalTime.parse(secondValue);

            if (startHour.isBefore(endHour))
                return false;

            return true;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
