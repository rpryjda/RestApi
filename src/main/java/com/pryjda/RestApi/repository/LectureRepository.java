package com.pryjda.RestApi.repository;

import com.pryjda.RestApi.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
