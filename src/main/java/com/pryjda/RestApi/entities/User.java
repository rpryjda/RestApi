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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private int indexNumber;
    private boolean enabled;

    @ManyToMany(mappedBy = "attendanceList")
    @JsonIgnore
    private Set<Lecture> lectures = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_profile_id", unique = true)
    private UserProfile userProfile;
}
