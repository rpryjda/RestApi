package com.pryjda.RestApi.service;

import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.exceptions.WrongUserIdException;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.UserRepository;
import com.pryjda.RestApi.utils.UserResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Override
    public UserResponse createRecordIntoAttendanceListByUserId(Long lectureId, Long userId) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> userRepository.findById(userId)
                        .map(user -> {
                            lecture.getAttendanceList().add(user);
                            lectureRepository.save(lecture);
                            return UserResponseBuilder.getUserResponseFromUserAndUserProfile(user, user.getUserProfile());
                        })
                        .orElseThrow(() -> new WrongUserIdException("number: " + userId + " is wrong user id")))
                .orElseThrow(() -> new WrongLectureIdException("number: " + lectureId + " is wrong lecture id"));
    }

    @Override
    public boolean createRecordIntoAttendanceListByUserEmail(Long lectureId, String userEmail) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> userRepository.findUserByEmail(userEmail)
                        .map(user -> {
                            lecture.getAttendanceList().add(user);
                            lectureRepository.save(lecture);
                            return true;
                        })
                        .orElse(false))
                .orElseThrow(() -> new WrongLectureIdException("number: " + lectureId + " is wrong lecture id"));
    }

    @Override
    public boolean createRecordIntoAttendanceListByUserIndexNumber(Long lectureId, int indexNumber) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> userRepository.findUserByIndexNumber(indexNumber)
                        .map(user -> {
                            lecture.getAttendanceList().add(user);
                            lectureRepository.save(lecture);
                            return true;
                        })
                        .orElse(false))
                .orElseThrow(() -> new WrongLectureIdException("number: " + lectureId + " is wrong lecture id"));
    }
}
