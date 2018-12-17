package com.pryjda.RestApi.controller;

import com.google.gson.Gson;

import com.pryjda.RestApi.model.request.StudentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentControllerTestIn {

    @Autowired
    private MockMvc mockMvc;

    private StudentRequest studentRequest;

    private String studentRequestJson;

    private Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        studentRequest = new StudentRequest();
        studentRequest.setName("Robert");
        studentRequest.setSurname("Mickiewicz");
        studentRequest.setEmail("robert.mickiewicz@wp.pl");
        studentRequest.setPassword("999999");
        studentRequest.setIndexNumber(100399);
        studentRequest.setAcademicYear("Second");
        studentRequest.setCourseOfStudy("Civil Engineering");

        studentRequestJson = gson.toJson(studentRequest);
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin123", roles = "ADMIN")
    public void shouldReturnListOfStudentsInJsonAndStatus200OK() throws Exception {
        this.mockMvc
                .perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].surname")
                        .value("NowakTest"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin123", roles = "ADMIN")
    public void shouldCreateStudentAndReturnStudentRespondInJsonAndStatus201Created() throws Exception {
        this.mockMvc
                .perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentRequestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.email")
                        .value("robert.mickiewicz@wp.pl"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin123", roles = "ADMIN")
    public void shouldUpdateStudentByIdAndReturnStatus204NoContent() throws Exception {
        long id = 3;
        this.mockMvc
                .perform(put("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentRequestJson))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin123", roles = "ADMIN")
    public void shouldReturnStatus404NotFoundWhenTryToUpdateStudentWithWrongId() throws Exception {
        long id = 8;
        this.mockMvc
                .perform(put("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentRequestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
