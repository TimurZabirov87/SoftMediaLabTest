package com.example.softmedialabtest;

import com.example.softmedialabtest.model.grade.Grade;
import com.example.softmedialabtest.model.grade.dto.GradeDto;
import com.example.softmedialabtest.model.grade.dto.UpdateGradeRequest;
import com.example.softmedialabtest.model.student.Student;
import com.example.softmedialabtest.model.student.dto.NewStudentDto;
import com.example.softmedialabtest.model.student.dto.StudentWithIdDto;
import com.example.softmedialabtest.repository.GradesRepository;
import com.example.softmedialabtest.repository.StudentsRepository;
import com.example.softmedialabtest.service.grade.GradeService;
import com.example.softmedialabtest.service.student.StudentService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GradesControllerTest {


    private MockMvc mvc;

    private final GradeService gradeService;
    private final ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
        mapper.registerModule(new JavaTimeModule());
    }


    @Test
    void GET_All_ShouldReturnAllGrades() throws Exception {

        MvcResult getAllGradesResult = mvc.perform(get("/grades")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].text", equalTo("неуд")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].text", equalTo("уд")))
                .andExpect(jsonPath("$[2].id", is(4)))
                .andExpect(jsonPath("$[2].text", equalTo("хор")))
                .andExpect(jsonPath("$[3].id", is(5)))
                .andExpect(jsonPath("$[3].text", equalTo("отл")))

                .andReturn();

        List<GradeDto> grades = mapper.readValue(
                getAllGradesResult.getResponse().getContentAsString(),
                new TypeReference<List<GradeDto>>() {
                });
        List<Long> ids = grades.stream().map(GradeDto::getId).toList();

        assertEquals(grades.size(), 4);
        assertTrue(ids.containsAll(List.of(2L, 3L, 4L, 5L)));
    }

    @Test
    void GET_BY_ID_ShouldReturnGradeWithSpecifiedId() throws Exception {

        mvc.perform(get("/grades/{id}", 4)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.text", equalTo("хор")));

    }

    @Test
    void UPDATE_BY_ID_ShouldUpdateTextForGradeWithSpecifiedId() throws Exception {

        String updateRequest = "{\"text\":\"отлично\"}";


        mvc.perform(patch("/grades/{id}", 5)
                        .content(updateRequest)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.text", equalTo("отлично")));

        //assert that grade with id "5" changed its "text"
        Grade grade5 = gradeService.findById(5L);

        assertEquals(grade5.getText(), "отлично");

        //return text name back, for correct run all tests
        gradeService.updateGrade(5L, new UpdateGradeRequest("отл"));
    }
}
