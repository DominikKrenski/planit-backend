package com.dominik.backend.validator;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by dominik on 07.04.17.
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private final Logger logger = LoggerFactory.getLogger(PasswordMatchValidator.class);

    private String password;
    private String passwordRepeated;

    @Override
    public void initialize(final PasswordMatch constraintAnnotation) {

        logger.info("INICJALIZACJA WALIDATORA PASSWORD_MATCH");

        password = constraintAnnotation.first();
        passwordRepeated = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        try {

            final String firstValue = BeanUtils.getProperty(value, password);
            final String secondValue = BeanUtils.getProperty(value, passwordRepeated);

            if (firstValue.equals(secondValue)) {
                return true;
            }

            return false;
        }
        catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
