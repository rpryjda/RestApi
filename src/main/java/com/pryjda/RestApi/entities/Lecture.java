package com.pryjda.RestApi.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @ManyToMany
    @JoinTable(
            name = "user_lecture",
            joinColumns = {@JoinColumn(name = "lecture_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> attendanceList = new HashSet<>();
}
