package com.pryjda.RestApi.controller;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LectureController {

    @Autowired
    private LectureRepository lectureRepository;

    @GetMapping(name = "/lectures")
    public List<Lecture> retrieveAllLectures() {
        return lectureRepository.findAll();
    }

    @PostMapping(name = "/lectures")
    public Lecture createLecture(@RequestBody Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @PutMapping("/lectures/{id}")
    public Lecture updateStudentById(@PathVariable(value = "id") Long id, @RequestBody Lecture changedLecture) {

        return lectureRepository.findById(id)
                .map(lecture -> {
                    lecture.setDescription(changedLecture.getDescription());
                    lecture.setTitle(changedLecture.getTitle());
                    lecture.setLecturer(changedLecture.getLecturer());
                    return lectureRepository.save(lecture);
                })
                .orElseGet(() -> {
                    changedLecture.setId(id);
                    return lectureRepository.save(changedLecture);
                });
    }

    @DeleteMapping("/lectures/{id}")
    public void deleteLecture(@PathVariable(value = "id") Long id) {

        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecture with id=" + id + " doesn't exist"));
        lectureRepository.delete(lecture);
    }

}
