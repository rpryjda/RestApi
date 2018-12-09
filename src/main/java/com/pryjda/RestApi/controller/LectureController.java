package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;
import com.pryjda.RestApi.service.LectureService;
import com.pryjda.RestApi.shared.dto.LectureDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LectureController {

    @Autowired
    private LectureService lectureService;

    private static final ModelMapper mapper = new ModelMapper();

    @GetMapping("/lectures")
    public ResponseEntity<List<LectureResponse>> retrieveAllLectures() {
        List<LectureDTO> lecturesDTO = lectureService.getLectures();
        List<LectureResponse> lecturesResponse = new ArrayList<>();
        for (LectureDTO item : lecturesDTO) {
            lecturesResponse.add(mapper.map(item, LectureResponse.class));
        }

        return lecturesResponse
                .stream()
                .findFirst()
                .map(item -> new ResponseEntity<>(lecturesResponse, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/lectures")
    public ResponseEntity<LectureResponse> createLecture(@RequestBody LectureRequest lectureRequest) {
        LectureDTO lectureDTO = mapper.map(lectureRequest, LectureDTO.class);
        LectureDTO createdLectureDTO = lectureService.createLecture(lectureDTO);
        LectureResponse lectureResponse = mapper.map(createdLectureDTO, LectureResponse.class);
        return new ResponseEntity<>(lectureResponse, HttpStatus.CREATED);
    }

    @PutMapping("/lectures/{id}")
    public ResponseEntity<?> updateLectureById(@PathVariable(value = "id") Long id, @RequestBody LectureRequest lectureRequest) {
        LectureDTO lectureDTO = mapper.map(lectureRequest, LectureDTO.class);

        boolean isUpdated = lectureService.updateLecture(id, lectureDTO);
        if (isUpdated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<?> deleteLectureById(@PathVariable(value = "id") Long id) {

        boolean isDeleted = lectureService.deleteLecture(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
