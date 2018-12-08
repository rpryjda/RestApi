package com.pryjda.RestApi.service;

public interface AttendanceService {

    boolean createRecordIntoAttendanceList(Long lectureID, String studentEmail);
}
