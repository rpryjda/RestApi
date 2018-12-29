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
    @WithMockUser(roles = "ADMIN")
    void shouldAssignUserToAttendanceListAndReturnStatus201Created() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","1","1"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotAssignUserToAttendanceListAndReturnStatus404NotFoundWhenIsWrongUserId() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","1","7"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotAssignUserToAttendanceListAndReturnStatus404NotFoundWhenIsWrongLectureId() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","7","1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldNotAssignUserToAttendanceListAndReturnStatus403ForbiddenWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}","6","3"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "USER")
    void shouldAssignLoggedByEmailUserToAttendanceListAndReturnStatus201Created() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id}","3"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "102033", roles = "USER")
    void shouldAssignLoggedByIndexNumberUserToAttendanceListAndReturnStatus201Created() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id}","3"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "piotr.rybka@gmail.com", roles = "OTHER")
    void shouldReturnStatus403ForbiddenWhenTryToAssignLoggedUserToAttendanceListButUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id}","3"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "wrongEmail@gmail.com", roles = "USER")
    void shouldReturnStatus404NotFoundWhenTryToAssignNotLoggedUserToAttendanceList() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id}","3"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "marcel.chrobry@gmail.com", roles = "USER")
    void shouldReturnStatus404NotFoundWhenTryToAssignLoggedUserToAttendanceListAndIndicatedLectureNotExists() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id}","9"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}