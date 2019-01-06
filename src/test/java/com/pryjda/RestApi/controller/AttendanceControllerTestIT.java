package com.pryjda.RestApi.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AttendanceControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(roles = "ADMIN")
    void shouldAssignUserToAttendanceListByAdminAccountAndReturnStatus201Created() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}", "4", "1"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotAssignUserToAttendanceListByAdminAccountAndReturnStatus404NotFoundWhenIsWrongUserId() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}", "1", "7"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotAssignUserToAttendanceListByAdminAccountAndReturnStatus404NotFoundWhenIsWrongLectureId() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}", "7", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "102000", roles = "USER")
    void shouldNotAssignUserToAttendanceListByStudentAccountAndReturnStatus403ForbiddenWhenIsIndicatedIdOfNotLoggedUser() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}", "6", "3"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(username = "102000", roles = "USER")
    void shouldAssignUserToAttendanceListByStudentAccountAndReturnStatus201CreatedWhenIsIndicatedIdOfLoggedUser() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}", "6", "4"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldNotAssignUserToAttendanceListAndReturnStatus403ForbiddenWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(post("/registry/lectures/{id_lecture}/users/{id_user}", "1", "1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnFullAttendanceListInJsonForAdminAccountAndReturnStatus200OK() throws Exception {
        mockMvc
                .perform(get("/lectures/{id_lecture}/attendance-list", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[2].id", is(4)))
                .andExpect(jsonPath("$[3].id", is(5)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnEmptyAttendanceListInJsonForAdminAccountAndReturnStatus200OK() throws Exception {
        mockMvc
                .perform(get("/lectures/{id_lecture}/attendance-list", "6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "marcel.chrobry@gmail.com", roles = "USER")
    void shouldReturnAttendanceListInJsonForStudentAccountThatContainsOnlyLoggedUserAndReturnStatus200OK() throws Exception {
        mockMvc
                .perform(get("/lectures/{id_lecture}/attendance-list", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(4)));
    }

    @Test
    @WithMockUser(username = "marcel.chrobry@gmail.com", roles = "USER")
    void shouldReturnEmptyAttendanceListInJsonForStudentAccountAndReturnStatus200OK() throws Exception {
        mockMvc
                .perform(get("/lectures/{id_lecture}/attendance-list", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotReturnAttendanceListInJsonAndReturnStatus404NotFoundWhenCallingWrongLectureId() throws Exception {
        mockMvc
                .perform(get("/lectures/{id_lecture}/attendance-list", "11")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}