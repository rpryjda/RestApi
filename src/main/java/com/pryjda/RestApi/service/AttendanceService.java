package com.pryjda.RestApi.service;

import com.pryjda.RestApi.model.response.UserResponse;
import org.springframework.security.access.prepost.PostAuthorize;

public interface AttendanceService {

    @PostAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(returnObject.getEmail())) or " +
            "(authentication.getName().equals(returnObject.getIndexNumber())))")
    UserResponse createRecordIntoAttendanceListByUserId(Long lectureId, Long userId);
}
