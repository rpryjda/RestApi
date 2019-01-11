package com.pryjda.RestApi.model.validation.validators;

import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueIndexNumberValidator implements ConstraintValidator<UniqueIndexNumber, Integer> {

    private UserRepository userRepository;

    @Autowired
    public UniqueIndexNumberValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Integer indexNumber, ConstraintValidatorContext constraintValidatorContext) {

        if (indexNumber != null) {
            Optional<User> userFromDatabase = userRepository.findUserByIndexNumber(indexNumber);
            return !userFromDatabase.isPresent();
        }
        return true;
    }
}