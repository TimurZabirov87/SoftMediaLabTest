package com.example.softmedialabtest;

import com.example.softmedialabtest.controller.StudentsController;
import com.example.softmedialabtest.model.student.dto.NewStudentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class StudentsControllerTest {


    @Autowired
    private MockMvc mvc;


    private final ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    @Test
    void saveNewUser() throws Exception {

        NewStudentDto newStudent = new NewStudentDto("Брекоткин Дмитрий Владиславович", LocalDate.of(1970, 3, 28));

        mvc.perform(post("/students")
                        .content(mapper.writeValueAsString(newStudent))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", is(newStudent.getFullName())))
                .andExpect(jsonPath("$.birthDate", is(newStudent.getBirthDate())));
    }

}
