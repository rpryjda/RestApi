package com.pryjda.RestApi.model.validation.validators;

import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email != null) {
            Optional<User> userFromDatabase = userRepository.findUserByEmail(email);
            return !userFromDatabase.isPresent();
        }
        return true;
    }
}