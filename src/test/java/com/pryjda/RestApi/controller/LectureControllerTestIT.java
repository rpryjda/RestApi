package com.pryjda.RestApi.controller;

import com.google.gson.*;
import com.pryjda.RestApi.model.request.LectureRequest;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LectureControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    private static LectureRequest lectureRequest;

    private static String lectureRequestJson;

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (localDateTime, type,
                                                     jsonSerializationContext) ->
                            new JsonPrimitive(localDateTime
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
            .create();

    @BeforeAll
    static void build() {
        lectureRequest = new LectureRequest();
        lectureRequest.setTitle("JavaDev TEST");
        lectureRequest.setDescription("Java programming");
        lectureRequest.setLecturer("James Tyson");
        lectureRequest.setDate(LocalDateTime.of(2020, 1, 21, 17, 30));

        lectureRequestJson = gson.toJson(lectureRequest);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnListOfLecturesWithFullAttendanceListsInJsonForAdminAccountAndStatus200OK() throws Exception {
        mockMvc.perform(get("/lectures")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].attendanceList").isArray())
                .andExpect(jsonPath("$[0].attendanceList", hasSize(4)))
                .andExpect(jsonPath("$[0].title", is("JavaDev 1 TEST")))
                .andExpect(jsonPath("$[0].attendanceList[0].id", is(2)))
                .andExpect(jsonPath("$[5].attendanceList").isArray())
                .andExpect(jsonPath("$[5].attendanceList", hasSize(0)))
                .andExpect(jsonPath("$[5].title", is("JavaDev 6 TEST")));
    }

    @Test
    @WithMockUser(username = "102000", roles = "USER")
    void shouldReturnListOfLecturesInJsonThatIncludeOnlyLoggedUserInAttendanceListForStudentAccountAndReturnStatus200OK() throws Exception {
        mockMvc.perform(get("/lectures")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].attendanceList").isArray())
                .andExpect(jsonPath("$[0].attendanceList", hasSize(1)))
                .andExpect(jsonPath("$[0].attendanceList[0].id", is(4)));
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldReturnStatus403ForbiddenAndNotReturnListOfLecturesWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(get("/lectures")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldGetLectureResponseObjectWithFullAttendanceListInJsonForAdminAccountAndStatus200OK() throws Exception {
        mockMvc
                .perform(get("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("JavaDev 3 TEST")))
                .andExpect(jsonPath("$.attendanceList").isArray())
                .andExpect(jsonPath("$.attendanceList", hasSize(5)))
                .andExpect(jsonPath("$.attendanceList[0].id", is(1)));
    }

    @Test
    @WithMockUser(username = "102000", roles = "USER")
    void shouldGetLectureResponseObjectInJsonThatIncludeOnlyLoggedUserInAttendanceListForStudentAccountAndReturnStatus200OK() throws Exception {
        mockMvc
                .perform(get("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("JavaDev 3 TEST")))
                .andExpect(jsonPath("$.attendanceList").isArray())
                .andExpect(jsonPath("$.attendanceList", hasSize(1)))
                .andExpect(jsonPath("$.attendanceList[0].id", is(4)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnStatus404NotFoundWhenTryToGetLectureWithWrongId() throws Exception {
        mockMvc
                .perform(get("/lectures/{id}", "11")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldReturnStatus403ForbiddenAndNotReturnUserResponseObjectWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(get("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(roles = "ADMIN")
    void shouldCreateLectureAndReturnLectureResponseObjectInJsonAndStatus201CreatedForAdminAccount() throws Exception {
        mockMvc
                .perform(post("/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title", is("JavaDev TEST")))
                .andExpect(jsonPath("$.date", is("2020-01-21 17:30")))
                .andExpect(jsonPath("$.attendanceList", hasSize(0)));
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnStatus403ForbiddenAndNotCreateLectureWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(post("/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateLectureByIdAndReturnStatus204NoContentForAdminAccount() throws Exception {
        mockMvc
                .perform(put("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnStatus404NotFoundWhenTryToUpdateLectureWithWrongId() throws Exception {
        mockMvc
                .perform(put("/lectures/{id}", "11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldNotUpdateLectureByIdAndReturnStatus403ForbiddenWhenUserIsNotAuthorized() throws Exception {
        mockMvc
                .perform(put("/lectures/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(lectureRequestJson))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteLectureByIdAndReturnStatus200OKForAdminAccountWhenLectureHasNotTakenPlaceYet() throws Exception {
        mockMvc
                .perform(delete("/lectures/{id}", "6"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldNotDeleteLectureByIdAndReturnStatus403ForbiddenForAdminAccountWhenLectureTookPlace() throws Exception {
        mockMvc
                .perform(delete("/lectures/{id}", "1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnStatus404NotFoundWhenTryToDeleteLectureWithWrongId() throws Exception {
        mockMvc
                .perform(delete("/lectures/{id}", "11"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "OTHER")
    void shouldReturnStatus403ForbiddenWhenTryToDeleteByNotAuthorizedUser() throws Exception {
        mockMvc
                .perform(delete("/lectures/{id}", "6"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}