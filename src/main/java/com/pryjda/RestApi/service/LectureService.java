package com.pryjda.RestApi.service;

import com.pryjda.RestApi.entities.Lecture;

import java.util.List;

public interface LectureService {

    List<Lecture> getLectures();

    Lecture createLecture(Lecture lecture);

    boolean updateLecture(Long lectureId, Lecture lecture);

    boolean deleteLecture(Long lectureId);
}
