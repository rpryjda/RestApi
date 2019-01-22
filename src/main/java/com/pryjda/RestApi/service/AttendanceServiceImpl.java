package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.exceptions.WrongUserIdException;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.UserRepository;
import com.pryjda.RestApi.utils.UserResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.TreeSet;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final UserRepository userRepository;

    private final LectureRepository lectureRepository;

    @Autowired
    public AttendanceServiceImpl(UserRepository userRepository, LectureRepository lectureRepository) {
        this.userRepository = userRepository;
        this.lectureRepository = lectureRepository;
    }

    @Override
    @Transactional
    public UserResponse createRecordIntoAttendanceListByUserId(Long lectureId, Long userId) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> userRepository.findById(userId)
                        .map(user -> {
                            lecture.getAttendanceList().add(user);
                            return UserResponseBuilder.getUserResponseFromUserAndUserProfile(user, user.getUserProfile());
                        })
                        .orElseThrow(() -> new WrongUserIdException("number: " + userId + " is wrong user id")))
                .orElseThrow(() -> new WrongLectureIdException("number: " + lectureId + " is wrong lecture id"));
    }

    @Override
    public Set<UserResponse> getAttendanceListFromLectureId(Long lectureId) {

        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    Set<User> users = lecture.getAttendanceList();
                    Set<UserResponse> usersResponse = new TreeSet<>((x, y) -> (int) (x.getId() - y.getId()));
                    users.stream()
                            .forEach(user -> usersResponse.add(UserResponseBuilder
                                    .getUserResponseFromUserAndUserProfile(user, user.getUserProfile())));
                    return usersResponse;
                })
                .orElseThrow(() -> new WrongLectureIdException("number: " + lectureId + " is wrong lecture id"));
    }
}
