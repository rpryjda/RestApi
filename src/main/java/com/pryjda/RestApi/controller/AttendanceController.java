package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/registry/lectures/{id_lecture}/users/{id_user}")
    public ResponseEntity<?> assignUserToAttendanceList(@PathVariable(value = "id_lecture") Long idLecture,
                                                        @PathVariable(value = "id_user") Long idUser) {
        attendanceService
                .createRecordIntoAttendanceListByUserId(idLecture, idUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
