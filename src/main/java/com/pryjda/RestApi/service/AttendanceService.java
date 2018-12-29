package com.pryjda.RestApi.service;

import com.pryjda.RestApi.model.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AttendanceService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    UserResponse createRecordIntoAttendanceListByUserId(Long lectureId, Long userId);

    @PreAuthorize("hasRole('ROLE_USER')")
    boolean createRecordIntoAttendanceListByUserEmail(Long lectureID, String userEmail);

    @PreAuthorize("hasRole('ROLE_USER')")
    boolean createRecordIntoAttendanceListByUserIndexNumber(Long lectureID, int indexNumber);
}
