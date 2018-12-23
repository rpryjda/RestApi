package com.pryjda.RestApi.controller;

import com.google.gson.Gson;

import com.pryjda.RestApi.model.request.UserRequest;
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
public class UserControllerTestIn {

    @Autowired
    private MockMvc mockMvc;

    private UserRequest userRequest;

    private String userRequestJson;

    private Gson gson = new Gson();

    @BeforeEach
    public void setUp() {
        userRequest = new UserRequest();
        userRequest.setName("Robert");
        userRequest.setSurname("Mickiewicz");
        userRequest.setEmail("robert.mickiewicz@wp.pl");
        userRequest.setPassword("999999");
        userRequest.setIndexNumber(100399);
        userRequest.setAcademicYear("Second");
        userRequest.setCourseOfStudy("Civil Engineering");

        userRequestJson = gson.toJson(userRequest);
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin123", roles = "ADMIN")
    public void shouldReturnListOfUsersInJsonAndStatus200OK() throws Exception {
        this.mockMvc
                .perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].surname")
                        .value("NowakTest"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin123", roles = "ADMIN")
    public void shouldCreateUserAndReturnUserRespondInJsonAndStatus201Created() throws Exception {
        this.mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.email")
                        .value("robert.mickiewicz@wp.pl"));
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin123", roles = "ADMIN")
    public void shouldUpdateUserByIdAndReturnStatus204NoContent() throws Exception {
        long id = 3;
        this.mockMvc
                .perform(put("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "Admin", password = "admin123", roles = "ADMIN")
    public void shouldReturnStatus404NotFoundWhenTryToUpdateUserWithWrongId() throws Exception {
        long id = 8;
        this.mockMvc
                .perform(put("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
