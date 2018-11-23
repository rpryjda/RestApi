package com.pryjda.RestApi.repository;

import com.pryjda.RestApi.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
