package com.pryjda.RestApi.utils;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.model.response.UserResponse;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
public class AttendanceListForLectureService {

    @PostFilter("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(filterObject.getEmail())) or " +
            "(authentication.getName().equals(filterObject.indexNumber.toString())))")
    public Set<UserResponse> getAttendanceListFromLectureObject(Lecture lecture) {
        Set<User> users = lecture.getAttendanceList();
        Set<UserResponse> usersResponse = new TreeSet<>((x, y) -> (int) (x.getId() - y.getId()));
        users.stream()
                .forEach(user -> usersResponse.add(UserResponseBuilder
                        .getUserResponseFromUserAndUserProfile(user, user.getUserProfile())));
        return usersResponse;
    }
}
