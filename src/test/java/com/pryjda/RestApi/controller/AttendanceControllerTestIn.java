package com.pryjda.RestApi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AttendanceControllerTestIn {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldAssignUserToAttendanceListAndReturnStatus201Created() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","1","1"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldNotAssignUserToAttendanceListAndReturnStatus404NotFoundWhenIsWrongUserId() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","1","7"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldNotAssignUserToAttendanceListAndReturnStatus404NotFoundWhenIsWrongLectureId() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","7","1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "USER")
    void shouldAssignLoggedUserToAttendanceListAndReturnStatus201CreatedWhenUserIsAuthenticatedAndAuthorized() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","6","3"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "OTHER")
    void shouldNotAssignLoggedUserToAttendanceListAndReturnStatus403ForbiddenWhenUserIsAuthenticatedAndNotAuthorized() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","6","3"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "USER")
    void shouldNotAssignLoggedUserToAttendanceListAndReturnStatus403ForbiddenWhenWrongUserIdIsIndicated() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","6","2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "USER")
    void shouldNotAssignLoggedUserToAttendanceListAndReturnStatus404NotFoundWhenWrongLectureIdIsIndicated() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","7","3"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}