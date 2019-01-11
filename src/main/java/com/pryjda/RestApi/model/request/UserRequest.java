package com.pryjda.RestApi.model.request;

import com.pryjda.RestApi.model.validation.order.userRequest.*;
import com.pryjda.RestApi.model.validation.validators.StartWithUpperCaseLetter;
import com.pryjda.RestApi.model.validation.validators.UniqueEmail;
import com.pryjda.RestApi.model.validation.validators.UniqueIndexNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@RequiredArgsConstructor
public class UserRequest {

    @NotEmpty(message = "Name is required", groups = CreatingUserStepNo1.class)
    @Size(min = 2, message = "Name should have at least two characters",
            groups = {CreatingUserStepNo2.class, UpdatingUserStepNo1.class})
    @StartWithUpperCaseLetter(message = "Name should start with upper case letter",
            groups = {CreatingUserStepNo3.class, UpdatingUserStepNo2.class})
    private String name;

    @NotEmpty(message = "Surname is required", groups = CreatingUserStepNo1.class)
    @Size(min = 2, message = "Surname should have at least two characters",
            groups = {CreatingUserStepNo2.class, UpdatingUserStepNo1.class})
    @StartWithUpperCaseLetter(message = "Surname should start with upper case letter",
            groups = {CreatingUserStepNo3.class, UpdatingUserStepNo2.class})
    private String surname;

    @NotNull(message = "E-mail is required", groups = CreatingUserStepNo1.class)
    @Email(message = "Incorrect e-mail address",
            groups = {CreatingUserStepNo2.class, UpdatingUserStepNo1.class})
    @UniqueEmail(groups = {CreatingUserStepNo3.class, UpdatingUserStepNo2.class})
    private String email;

    @NotNull(message = "Password is required", groups = CreatingUserStepNo1.class)
    @Size(min = 3, max = 16, message = "Password should be between 3 and 16 characters",
            groups = CreatingUserStepNo2.class)
    @Null(message = "You cannot update password here", groups = UpdatingUserStepNo1.class)
    private String password;

    private String academicYear;

    private String courseOfStudy;

    @NotNull(message = "Index number is required", groups = CreatingUserStepNo1.class)
    @Positive(message = "Number of index should be more than zero", groups = CreatingUserStepNo2.class)
    @UniqueIndexNumber(groups = {CreatingUserStepNo3.class, UpdatingUserStepNo1.class})
    private int indexNumber;
}
