package com.pryjda.RestApi.controller;

import com.google.gson.Gson;
import com.pryjda.RestApi.model.request.LectureRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LectureControllerTestIn {

    @Autowired
    private MockMvc mockMvc;

    private LectureRequest lectureRequest;

    private String lectureRequestJson;

    private Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        lectureRequest = new LectureRequest();
        lectureRequest.setTitle("JavaDev TEST");
        lectureRequest.setDescription("Java programming");
        lectureRequest.setLecturer("James Tyson");

        lectureRequestJson = gson.toJson(lectureRequest);
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldReturnListOfLecturesInJsonAndStatus200OK() throws Exception {
        mockMvc.perform(get("/lectures")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("JavaDev 1 TEST"))
                .andExpect(jsonPath("$[0].attendanceList")
                        .isArray())
                .andExpect(jsonPath("$[0].attendanceList")
                        .isNotEmpty())
                .andExpect(jsonPath("$[5].attendanceList")
                        .isArray())
                .andExpect(jsonPath("$[5].attendanceList")
                        .isEmpty())
                .andExpect(jsonPath("$[4].attendanceList[1].id")
                        .value("2"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "OTHER")
    void shouldReturnStatus403ForbiddenAndNotReturnListOfLecturesWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(get("/lectures")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldGetLectureResponseObjectInJsonAndStatus200OK() throws Exception {
        mockMvc
                .perform(get("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title")
                        .value("JavaDev 3 TEST"))
                .andExpect(jsonPath("$.attendanceList[0].id")
                        .value("1"));
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldReturnStatus404NotFoundWhenTryToGetLectureWithWrongId() throws Exception {
        mockMvc
                .perform(get("/lectures/{id}","9")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "OTHER")
    void shouldReturnStatus403ForbiddenAndNotReturnUserResponseObjectWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(get("/lectures/{id}","3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldCreateLectureAndReturnLectureRespondInJsonAndStatus201Created() throws Exception {
        mockMvc
                .perform(post("/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                        .value("JavaDev TEST"))
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "OTHER")
    void shouldReturnStatus403ForbiddenAndNotCreateLectureWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(get("/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldUpdateLectureByIdAndReturnStatus204NoContent() throws Exception {
        mockMvc
                .perform(put("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldReturnStatus404NotFoundWhenTryToUpdateLectureWithWrongId() throws Exception {
        mockMvc
                .perform(put("/lectures/{id}", "9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "OTHER")
    void shouldNotUpdateLectureByIdAndReturnStatus403ForbiddenWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(put("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldDeleteLectureByIdAndReturnStatus200OK() throws Exception {
        mockMvc
                .perform(delete("/lectures/{id}", "3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "ADMIN")
    void shouldReturnStatus404NotFoundWhenTryToDeleteLectureWithWrongId() throws Exception {
        mockMvc
                .perform(delete("/lectures/{id}", "9"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Admin", roles = "USER")
    void shouldReturnStatus403ForbiddenWhenTryToDeleteByNotAuthorizedUser() throws Exception {
        mockMvc
                .perform(delete("/lectures/{id}", "9"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}