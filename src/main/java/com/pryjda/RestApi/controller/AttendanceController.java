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

    @PostMapping("/registry/lectures/{id_lecture}/users/{id_user}")
    public ResponseEntity<?> assignUserToAttendanceList(@PathVariable(value = "id_lecture") Long idLecture,
                                                        @PathVariable(value = "id_user") Long idUser) {
        attendanceService
                .createRecordIntoAttendanceListByUserId(idLecture, idUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/registry/lectures/{id_lecture}")
    public ResponseEntity<?> assignLoggedUserToAttendanceList(@PathVariable(value = "id_lecture") Long idLecture) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();

        boolean isUserAddedToAttendanceList;
        if (Helpers.isNumber(authName)) {
            isUserAddedToAttendanceList = attendanceService
                    .createRecordIntoAttendanceListByUserIndexNumber(idLecture, Integer.parseInt(authName));
        } else {
            isUserAddedToAttendanceList = attendanceService
                    .createRecordIntoAttendanceListByUserEmail(idLecture, authName);
        }
        if (isUserAddedToAttendanceList) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
