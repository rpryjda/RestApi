package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;
import com.pryjda.RestApi.model.validation.order.lectureRequest.ConstraintsOrderForLectureRequestAndPostMethod;
import com.pryjda.RestApi.model.validation.order.lectureRequest.ConstraintsOrderForLectureRequestAndPutMethod;
import com.pryjda.RestApi.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class LectureController {

    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/lectures")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<LectureResponse>> retrieveAllLectures() {
        List<LectureResponse> lecturesResponse = lectureService.getLectures();

        return lecturesResponse
                .stream()
                .findFirst()
                .map(item -> new ResponseEntity<>(lecturesResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/lectures/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<LectureResponse> getLecture(@PathVariable(value = "id") Long id) {
        LectureResponse lectureResponse = lectureService.getLectureById(id);
        return new ResponseEntity<>(lectureResponse, HttpStatus.OK);
    }

    @PostMapping("/lectures")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LectureResponse> createLecture(
            @Validated(value = ConstraintsOrderForLectureRequestAndPostMethod.class)
            @RequestBody LectureRequest lectureRequest) {

        LectureResponse createdLectureResponse = lectureService.createLecture(lectureRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLectureResponse.getId())
                .toUri();
        HttpHeaders locationHeader = new HttpHeaders();
        locationHeader.setLocation(location);
        return new ResponseEntity<>(createdLectureResponse, locationHeader, HttpStatus.CREATED);
    }

    @PutMapping("/lectures/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateLectureById(@PathVariable(value = "id") Long id,
                                               @Validated(value = ConstraintsOrderForLectureRequestAndPutMethod.class)
                                               @RequestBody LectureRequest lectureRequest) {

        boolean isUpdated = lectureService.updateLecture(id, lectureRequest);
        if (isUpdated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/lectures/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteLectureById(@PathVariable(value = "id") Long id) {

        boolean isDeleted = lectureService.deleteLecture(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
