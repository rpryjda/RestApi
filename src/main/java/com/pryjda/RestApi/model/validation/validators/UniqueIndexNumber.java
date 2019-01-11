package com.pryjda.RestApi.model.validation.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueIndexNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueIndexNumber {

    String message() default "That number of index already exists in database";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
