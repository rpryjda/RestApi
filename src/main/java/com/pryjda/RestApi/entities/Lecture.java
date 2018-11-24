package com.pryjda.RestApi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String lecturer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_lecture",
            joinColumns = {@JoinColumn(name = "lecture_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")})
    @JsonIgnore
    private Set<Student> attendanceList = new HashSet<>();

}
