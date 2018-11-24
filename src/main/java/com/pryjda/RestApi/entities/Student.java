package com.pryjda.RestApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String academicYear;
    private String courseOfStudy;
    private int indexNumber;

    @ManyToMany(mappedBy = "attendanceList")
    @JsonIgnore
    private Set<Lecture> lectures = new HashSet<>();

}
