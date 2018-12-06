package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LectureController {

    @Autowired
    private LectureRepository lectureRepository;

    @GetMapping("/lectures")
    public ResponseEntity<List<Lecture>> retrieveAllLectures() {

        return lectureRepository.findAll()
                .stream()
                .findFirst()
                .map(item -> new ResponseEntity<>(lectureRepository.findAll(), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/lectures")
    public ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture) {
        lectureRepository.save(lecture);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/lectures/{id}")
    public ResponseEntity<?> updateLectureById(@PathVariable(value = "id") Long id, @RequestBody Lecture changedLecture) {

        return lectureRepository.findById(id)
                .map(lecture -> {
                    lecture.setDescription(changedLecture.getDescription());
                    lecture.setTitle(changedLecture.getTitle());
                    lecture.setLecturer(changedLecture.getLecturer());
                    lectureRepository.save(lecture);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<?> deleteLectureById(@PathVariable(value = "id") Long id) {

        return lectureRepository.findById(id)
                .map(lecture -> {
                    lectureRepository.delete(lecture);
                    return new ResponseEntity<>(HttpStatus.OK);
                }).orElse(ResponseEntity.notFound().build());
    }
}
