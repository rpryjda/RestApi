package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    private final LectureRepository lectureRepository;

    @Autowired
    public SecurityServiceImpl(UserRepository userRepository, LectureRepository lectureRepository) {
        this.userRepository = userRepository;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public boolean isIdOfLoggedUser(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();

        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            Integer indexNumber = user.get().getIndexNumber();
            String email = user.get().getEmail();
            return indexNumber.toString().equals(authName)
                    ? true
                    : email.equals(authName);
        }
        return false;
    }

    @Override
    public boolean hasNotTakenPlaceYet(Long lectureId) {

        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    LocalDateTime lectureDate = lecture.getDate();
                    return lectureDate.isAfter(LocalDateTime.now());
                })
                .orElseThrow(() -> new WrongLectureIdException("number: " + lectureId + " is wrong lecture id"));
    }
}
