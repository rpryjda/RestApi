package com.pryjda.RestApi.service;

public interface AttendanceService {

    boolean createRecordIntoAttendanceListByUserEmail(Long lectureID, String userEmail);

    boolean createRecordIntoAttendanceListByUserIndexNumber(Long lectureID, int indexNumber);
}
