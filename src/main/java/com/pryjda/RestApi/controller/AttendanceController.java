package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.service.AttendanceService;
import com.pryjda.RestApi.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/registry/lectures/{id_lecture}")
    public ResponseEntity<?> assignLoggedUserToAttendanceList(@PathVariable(value = "id_lecture") Long idLecture) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();

        boolean isUserAddedToAttendanceListByUsingEmail = attendanceService
                .createRecordIntoAttendanceListByUserEmail(idLecture, authName);
        boolean isUserAddedToAttendanceListByUsingIndexNumber = false;
        if (Helpers.isNumber(authName)) {
            isUserAddedToAttendanceListByUsingIndexNumber = attendanceService
                    .createRecordIntoAttendanceListByUserIndexNumber(idLecture, Integer.parseInt(authName));
        }
        if (isUserAddedToAttendanceListByUsingEmail || isUserAddedToAttendanceListByUsingIndexNumber) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
