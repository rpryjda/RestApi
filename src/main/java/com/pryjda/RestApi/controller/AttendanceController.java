package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/registry/lectures/{id_lecture}/users/{id_user}")
    public ResponseEntity<?> assignUserToAttendanceList(@PathVariable(value = "id_lecture") Long idLecture,
                                                        @PathVariable(value = "id_user") Long idUser) {
        attendanceService
                .createRecordIntoAttendanceListByUserId(idLecture, idUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("lectures/{id_lecture}/attendance-list")
    public ResponseEntity<Set<UserResponse>> getAttendanceListForLecture(@PathVariable(value = "id_lecture") Long idLecture) {
        Set<UserResponse> usersResponse = attendanceService.getAttendanceListFromLectureId(idLecture);
        return new ResponseEntity<>(usersResponse, HttpStatus.OK);
    }
}
