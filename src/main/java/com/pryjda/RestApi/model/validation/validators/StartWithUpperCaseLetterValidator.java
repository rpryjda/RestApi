package com.pryjda.RestApi.model.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartWithUpperCaseLetterValidator implements ConstraintValidator<StartWithUpperCaseLetter, String> {

    @Override
    public boolean isValid(String text, ConstraintValidatorContext constraintValidatorContext) {
        if (text != null) {
            Character firstLetter = text.toCharArray()[0];
            return Character.isUpperCase(firstLetter);
        }
        return true;
    }
}
