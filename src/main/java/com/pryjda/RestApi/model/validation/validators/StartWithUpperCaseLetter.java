package com.pryjda.RestApi.model.validation.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StartWithUpperCaseLetterValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartWithUpperCaseLetter {

    String message() default "Field value should start with upper case letter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
