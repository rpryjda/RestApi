package com.pryjda.RestApi.service;

import com.pryjda.RestApi.model.response.UserResponse;

import java.util.Set;

public interface AttendanceService {

    UserResponse createRecordIntoAttendanceListByUserId(Long lectureId, Long userId);

    Set<UserResponse> getAttendanceListFromLectureId(Long lectureId);
}
