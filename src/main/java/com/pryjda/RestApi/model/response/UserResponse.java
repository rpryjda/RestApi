package com.pryjda.RestApi.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String academicYear;
    private String courseOfStudy;
    private int indexNumber;
}
