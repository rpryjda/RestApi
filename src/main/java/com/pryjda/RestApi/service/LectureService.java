package com.pryjda.RestApi.service;

import com.pryjda.RestApi.shared.dto.LectureDTO;

import java.util.List;

public interface LectureService {

    List<LectureDTO> getLectures();

    LectureDTO createLecture(LectureDTO lectureDTO);

    boolean updateLecture(Long lectureId, LectureDTO lectureDTO);

    boolean deleteLecture(Long lectureId);
}
