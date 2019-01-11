package com.pryjda.RestApi.controller;

import com.google.gson.Gson;

import com.pryjda.RestApi.model.request.UserRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    private static UserRequest userRequestCreated;

    private static UserRequest userRequestUpdated;

    private static String userRequestCreatedJson;

    private static String userRequestUpdatedJson;

    private String newPassword = "new password";

    private static Gson gson = new Gson();

    @BeforeAll
    public static void init() {
        userRequestCreated = new UserRequest();
        userRequestCreated.setName("Robert");
        userRequestCreated.setSurname("Mickiewicz");
        userRequestCreated.setEmail("robert.mickiewicz@wp.pl");
        userRequestCreated.setPassword("999999");
        userRequestCreated.setIndexNumber(100399);
        userRequestCreated.setAcademicYear("Second");
        userRequestCreated.setCourseOfStudy("Civil Engineering");

        userRequestCreatedJson = gson.toJson(userRequestCreated);

        userRequestUpdated = new UserRequest();
        userRequestUpdated.setName("Robert");
        userRequestUpdated.setSurname("Mickiewicz");
        userRequestUpdated.setEmail("robert.mickiewicz@wp.pl");
        userRequestUpdated.setIndexNumber(100399);
        userRequestUpdated.setAcademicYear("Second");
        userRequestUpdated.setCourseOfStudy("Civil Engineering");

        userRequestUpdatedJson = gson.toJson(userRequestUpdated);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnFullListOfUsersInJsonAndStatus200OkForAdminAccount() throws Exception {
        this.mockMvc
                .perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].surname", is("NowakTest")));
    }

    @Test
    @WithMockUser(username = "adam.kowalski@gmail.com", roles = "USER")
    public void shouldReturnListOfUsersWithOneLoggedUserInJsonAndStatus200OkForStudentAccount() throws Exception {
        this.mockMvc
                .perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].surname", is("KowalskiTest")));
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldReturnStatus403ForbiddenAndNotReturnListOfUsersWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetUserResponseObjectInJsonForAdminAccountAndStatus200OK() throws Exception {
        mockMvc
                .perform(get("/users/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.email", is("piotr.rybka@gmail.com")))
                .andExpect(jsonPath("$.surname", is("RybkaTest")));
    }

    @Test
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "USER")
    void shouldGetUserResponseObjectInJsonThatRepresentsLoggedUserForStudentAccountLoggedByEmailAndStatus200OK() throws Exception {
        mockMvc
                .perform(get("/users/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.email", is("piotr.rybka@gmail.com")))
                .andExpect(jsonPath("$.surname", is("RybkaTest")));
    }

    @Test
    @WithMockUser(username = "102033", roles = "USER")
    void shouldGetUserResponseObjectInJsonThatRepresentsLoggedUserForStudentAccountLoggedByIndexNumberAndStatus200OK() throws Exception {
        mockMvc
                .perform(get("/users/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.email", is("piotr.rybka@gmail.com")))
                .andExpect(jsonPath("$.surname", is("RybkaTest")));
    }

    @Test
    @WithMockUser(username = "adam.kowalski@gmail.com", roles = "USER")
    void shouldReturnStatus403ForbiddenAndNotGetUserResponseObjectInJsonForStudentAccountWhenTryToGetNotLoggedUser() throws Exception {
        mockMvc
                .perform(get("/users/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldReturnStatus403ForbiddenAndNotReturnResponseUserObjectInJsonWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(get("/users/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(roles = "ADMIN")
    public void shouldCreateUserAndReturnUserResponseObjectInJsonAndStatus201CreatedForAdminAccount() throws Exception {
        this.mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestCreatedJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.email", is("robert.mickiewicz@wp.pl")))
                .andExpect(jsonPath("$.surname", is("Mickiewicz")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnStatus403ForbiddenAndNotCreateUserWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestCreatedJson))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(roles = "ADMIN")
    public void shouldUpdateUserByIdAndReturnStatus204NoContentForAdminAccount() throws Exception {
        this.mockMvc
                .perform(put("/users/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestUpdatedJson))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnStatus404NotFoundWhenTryToUpdateUserWithWrongId() throws Exception {
        this.mockMvc
                .perform(put("/users/{id}", 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestUpdatedJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "USER")
    public void shouldUpdateLoggedUserAndReturnStatus204NoContentForStudentAccount() throws Exception {
        this.mockMvc
                .perform(put("/users/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestUpdatedJson))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "adam.kowalski@gmail.com", roles = "USER")
    public void shouldNotUpdateUserAndReturnStatus403ForbiddenForStudentAccountWhenTryUpdateNotLoggedUser() throws Exception {
        this.mockMvc
                .perform(put("/users/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestUpdatedJson))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldNotUpdateUserAndReturnStatus403ForbiddenWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(put("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestUpdatedJson))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteUsersByIdAndReturnStatus200OkForAdminAccount() throws Exception {
        mockMvc
                .perform(delete("/users/{id}", "5"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnStatus404NotFoundWhenTryToDeleteUserWithWrongId() throws Exception {
        mockMvc
                .perform(delete("/users/{id}", "7"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnStatus403ForbiddenWhenTryToDeleteUserByNotAuthorizedUser() throws Exception {
        mockMvc
                .perform(delete("/lectures/{id}", "5"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(roles = "ADMIN")
    public void shouldResetUserPasswordAndReturnStatus204NoContentForAdminAccount() throws Exception {
        this.mockMvc
                .perform(patch("/users/{id}/password", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldNotResetUserPasswordAndReturnStatus404NotFoundForAdminAccountWhenTryToResetPasswordForWrongUserId() throws Exception {
        this.mockMvc
                .perform(patch("/users/{id}/password", 7)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "USER")
    public void shouldResetLoggedUserPasswordAndReturnStatus204NoContentForStudentAccount() throws Exception {
        this.mockMvc
                .perform(patch("/users/{id}/password", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "adam.kowalski@gmail.com", roles = "USER")
    public void shouldNotResetUserPasswordAndReturnStatus403ForbiddenForStudentAccountWhenTryResetPasswordForNotLoggedUser() throws Exception {
        this.mockMvc
                .perform(patch("/users/{id}/password", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldReturnStatus403ForbiddenWhenTryToResetPasswordByNotAuthorizedUser() throws Exception {
        mockMvc
                .perform(patch("/users/{id}/password", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPassword))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
