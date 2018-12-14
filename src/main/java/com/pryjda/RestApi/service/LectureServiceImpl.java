package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.model.request.LectureRequest;
import com.pryjda.RestApi.model.response.LectureResponse;
import com.pryjda.RestApi.repository.LectureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    private static final ModelMapper mapper = new ModelMapper();

    @Override
    public List<LectureResponse> getLectures() {
        List<Lecture> lectures = lectureRepository.findAll();
        List<LectureResponse> lecturesResponse = new ArrayList<>();
        for (Lecture item : lectures) {
            lecturesResponse.add(mapper.map(item, LectureResponse.class));
        }
        return lecturesResponse;
    }

    @Override
    public LectureResponse createLecture(LectureRequest lectureRequest) {
        Lecture lecture = mapper.map(lectureRequest, Lecture.class);
        Lecture createdLecture = lectureRepository.save(lecture);
        LectureResponse createdLectureResponse = mapper.map(createdLecture, LectureResponse.class);
        return createdLectureResponse;
    }

    @Override
    public boolean updateLecture(Long lectureId, LectureRequest updatedLectureRequest) {
        Lecture updatedLecture = mapper.map(updatedLectureRequest, Lecture.class);

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
