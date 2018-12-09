package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;
import com.pryjda.RestApi.repository.LectureRepository;
import com.pryjda.RestApi.shared.dto.LectureDTO;
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
    public List<LectureDTO> getLectures() {
        List<Lecture> lectures = lectureRepository.findAll();
        List<LectureDTO> lecturesDTO = new ArrayList<>();
        for (Lecture item : lectures) {
            lecturesDTO.add(mapper.map(item, LectureDTO.class));
        }
        return lecturesDTO;
    }

    @Override
    public LectureDTO createLecture(LectureDTO lectureDTO) {
        Lecture lecture = mapper.map(lectureDTO, Lecture.class);
        Lecture createdLecture = lectureRepository.save(lecture);
        LectureDTO createdLectureDTO = mapper.map(createdLecture, LectureDTO.class);
        return createdLectureDTO;
    }

    @Override
    public boolean updateLecture(Long lectureId, LectureDTO updatedLectureDTO) {
        Lecture updatedLecture = mapper.map(updatedLectureDTO, Lecture.class);

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
