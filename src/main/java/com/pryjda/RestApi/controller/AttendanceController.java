package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.response.UserResponse;
import com.pryjda.RestApi.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "@securityServiceImpl.isIdOfLoggedUser(#idUser))")
    public ResponseEntity<?> assignUserToAttendanceList(@PathVariable(value = "id_lecture") Long idLecture,
                                                        @PathVariable(value = "id_user") Long idUser) {
        attendanceService
                .createRecordIntoAttendanceListByUserId(idLecture, idUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("lectures/{id_lecture}/attendance-list")
    @ResponseStatus(HttpStatus.OK)
    @PostFilter("hasRole('ROLE_ADMIN') or " +
            "(hasRole('ROLE_USER') and " +
            "(authentication.getName().equals(filterObject.getEmail())) or " +
            "(authentication.getName().equals(filterObject.indexNumber.toString())))")
    public Set<UserResponse> getAttendanceListForLecture(@PathVariable(value = "id_lecture") Long idLecture) {
        Set<UserResponse> usersResponse = attendanceService.getAttendanceListFromLectureId(idLecture);
        return usersResponse;
    }
}
