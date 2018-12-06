package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Override
    public List<Lecture> getLectures() {
        return lectureRepository.findAll();
    }

    @Override
    public Lecture createLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    @Override
    public boolean updateLecture(Long lectureId, Lecture updatedLecture) {
        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    lecture.setDescription(updatedLecture.getDescription());
                    lecture.setTitle(updatedLecture.getTitle());
                    lecture.setLecturer(updatedLecture.getLecturer());
                    lectureRepository.save(lecture);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean deleteLecture(Long lectureId) {

        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    lectureRepository.deleteById(lectureId);
                    return true;
                })
                .orElse(false);
    }
}
