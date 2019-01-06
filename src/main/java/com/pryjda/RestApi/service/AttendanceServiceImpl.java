package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.exceptions.WrongLectureIdException;
import com.pryjda.RestApi.exceptions.WrongUserIdException;
import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.repository.UserRepository;
import com.pryjda.RestApi.utils.UserResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

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
    public Set<UserResponse> getAttendanceListFromLectureObject(Lecture lecture) {
        Set<User> users = lecture.getAttendanceList();
        Set<UserResponse> usersResponse = new TreeSet<>((x, y) -> (int) (x.getId() - y.getId()));
        for (User itemUser : users) {
            usersResponse.add(UserResponseBuilder
                    .getUserResponseFromUserAndUserProfile(itemUser, itemUser.getUserProfile()));
        }
        return usersResponse;
    }

    @Override
    public Set<UserResponse> getAttendanceListFromLectureId(Long lectureId) {

        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    Set<User> users = lecture.getAttendanceList();
                    Set<UserResponse> usersResponse = new TreeSet<>((x, y) -> (int) (x.getId() - y.getId()));
                    for (User itemUser : users) {
                        usersResponse.add(UserResponseBuilder
                                .getUserResponseFromUserAndUserProfile(itemUser, itemUser.getUserProfile()));
                    }
                    return usersResponse;
                })
                .orElseThrow(() -> new WrongLectureIdException("number: " + lectureId + " is wrong lecture id"));
    }
}
