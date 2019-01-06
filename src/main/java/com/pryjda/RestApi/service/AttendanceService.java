package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.model.response.UserResponse;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Set;

public interface AttendanceService {

    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "@securityServiceImpl.isIdOfLoggedUser(#userId))")
    UserResponse createRecordIntoAttendanceListByUserId(Long lectureId, Long userId);

    @PostFilter("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(filterObject.getEmail())) or " +
            "(authentication.getName().equals(filterObject.indexNumber.toString())))")
    Set<UserResponse> getAttendanceListFromLectureObject(Lecture lecture);

    @PostFilter("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(filterObject.getEmail())) or " +
            "(authentication.getName().equals(filterObject.indexNumber.toString())))")
    Set<UserResponse> getAttendanceListFromLectureId(Long lectureId);
}
