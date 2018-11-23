package com.pryjda.RestApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String lecturer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_lecture",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "lecture_id", referencedColumnName = "id")})
    @JsonIgnore
    private Set<Student> attendanceList = new HashSet<>();

}
