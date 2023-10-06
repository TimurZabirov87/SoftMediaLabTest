package com.example.softmedialabtest;

import com.example.softmedialabtest.model.student.Student;
import com.example.softmedialabtest.model.student.dto.NewStudentDto;
import com.example.softmedialabtest.model.student.dto.StudentWithIdDto;
import com.example.softmedialabtest.repository.StudentsRepository;
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
public class StudentsControllerTest {


    private MockMvc mvc;

    private final StudentsRepository studentsRepository;
    private final StudentService studentService;
    private final ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
        mapper.registerModule(new JavaTimeModule());
        studentsRepository.deleteAll();
    }

    @Test
    void POST_shouldSaveNewStudentWithoutGrade() throws Exception {

        NewStudentDto newStudent = new NewStudentDto("Брекоткин Дмитрий Владиславович", LocalDate.of(1970, 3, 28));

        mvc.perform(post("/students")
                        .content(mapper.writeValueAsString(newStudent))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", is(newStudent.getFullName())))
                .andExpect(jsonPath("$.birthDate", equalTo(newStudent.getBirthDate().toString())))
                .andExpect(jsonPath("$.grade.id", equalTo(null)))
                .andExpect(jsonPath("$.grade.text", equalTo(null)));

        //check that student added on db
        MvcResult getAllStudentsResult = mvc.perform(get("/students")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<StudentWithIdDto> students = mapper.readValue(getAllStudentsResult.getResponse().getContentAsString(), new TypeReference<List<StudentWithIdDto>>() {
        });

        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getBirthDate(), newStudent.getBirthDate());

    }

    @Test
    void POST_shouldSaveNewStudentWithGrade() throws Exception {

        NewStudentDto newStudent = new NewStudentDto("Брекоткин Дмитрий Владиславович", LocalDate.of(1970, 3, 28), 3L);

        mvc.perform(post("/students")
                        .content(mapper.writeValueAsString(newStudent))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", is(newStudent.getFullName())))
                .andExpect(jsonPath("$.birthDate", equalTo(newStudent.getBirthDate().toString())))
                .andExpect(jsonPath("$.grade.id", equalTo(3)))
                .andExpect(jsonPath("$.grade.text", equalTo("уд")));

        //check that student added on db
        MvcResult getAllStudentsResult = mvc.perform(get("/students")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<StudentWithIdDto> students = mapper.readValue(getAllStudentsResult.getResponse().getContentAsString(), new TypeReference<List<StudentWithIdDto>>() {
        });

        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getBirthDate(), newStudent.getBirthDate());
    }

    @Test
    void POST_shouldDontSaveNewStudentWithWrongGrade1() throws Exception {

        NewStudentDto newStudent = new NewStudentDto("Брекоткин Дмитрий Владиславович", LocalDate.of(1970, 3, 28), 1L);

        mvc.perform(post("/students")
                        .content(mapper.writeValueAsString(newStudent))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.error", equalTo("Method Argument Not Valid")))
                .andExpect(jsonPath("$.errors", equalTo(List.of("gradeId is invalid: should be 2 <= grade <= 5"))));


        //check that student don't added on db
        MvcResult getAllStudentsResult = mvc.perform(get("/students")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(getAllStudentsResult.getResponse().getContentAsString(), List.of().toString());
    }

    @Test
    void POST_shouldDontSaveNewStudentWithWrongGrade6() throws Exception {

        NewStudentDto newStudent = new NewStudentDto("Брекоткин Дмитрий Владиславович", LocalDate.of(1970, 3, 28), 6L);

        mvc.perform(post("/students")
                        .content(mapper.writeValueAsString(newStudent))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.error", equalTo("Method Argument Not Valid")))
                .andExpect(jsonPath("$.errors", equalTo(List.of("gradeId is invalid: should be 2 <= grade <= 5"))));


        //check that student don't added on db
        MvcResult getAllStudentsResult = mvc.perform(get("/students")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(getAllStudentsResult.getResponse().getContentAsString(), List.of().toString());
    }

    @Test
    void GET_All_ShouldReturnAllStudents() throws Exception {

        Student newStudent1 = setupFirstStudent();
        Student newStudent2 = setupSecondStudent();

        MvcResult getAllStudentsResult = mvc.perform(get("/students")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName", is(newStudent1.getFullname())))
                .andExpect(jsonPath("$[0].birthDate", equalTo(newStudent1.getBirthdate().toString())))
                .andExpect(jsonPath("$[0].grade.id", equalTo(newStudent1.getGrade().getId().intValue())))
                .andExpect(jsonPath("$[1].fullName", is(newStudent2.getFullname())))
                .andExpect(jsonPath("$[1].birthDate", equalTo(newStudent2.getBirthdate().toString())))
                .andExpect(jsonPath("$[1].grade.id", equalTo(null)))
                .andReturn();

        List<StudentWithIdDto> students = mapper.readValue(
                getAllStudentsResult.getResponse().getContentAsString(),
                new TypeReference<List<StudentWithIdDto>>() {
                });
        List<Long> ids = students.stream().map(StudentWithIdDto::getId).toList();

        assertTrue(ids.containsAll(List.of(newStudent1.getId(), newStudent2.getId())));
    }

    @Test
    void GET_BY_ID_ShouldReturnStudentsWithSpecifiedId() throws Exception {

        Student newStudent1 = setupFirstStudent();
        Student newStudent2 = setupSecondStudent();


        mvc.perform(get("/students/{id}", newStudent1.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is(newStudent1.getFullname())))
                .andExpect(jsonPath("$.birthDate", equalTo(newStudent1.getBirthdate().toString())))
                .andExpect(jsonPath("$.grade.id", equalTo(newStudent1.getGrade().getId().intValue())));

    }

    @Test
    void UPDATE_BY_ID_ShouldUpdateGradeForStudentWithSpecifiedId() throws Exception {

        Student newStudent1 = setupFirstStudent();
        String updateRequest = "{\"gradeId\":4}";


        mvc.perform(patch("/students/{id}", newStudent1.getId())
                        .content(updateRequest)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is(newStudent1.getFullname())))
                .andExpect(jsonPath("$.birthDate", equalTo(newStudent1.getBirthdate().toString())))
                .andExpect(jsonPath("$.grade.id", equalTo(4)));

    }


    @Test
    void UPDATE_BY_ID_ShouldUpdateBirthdateForStudentWithSpecifiedId() throws Exception {

        Student newStudent1 = setupFirstStudent();
        String updateRequest = "{\"birthDate\":\"1970-03-27\"}";


        mvc.perform(patch("/students/{id}", newStudent1.getId())
                        .content(updateRequest)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is(newStudent1.getFullname())))
                .andExpect(jsonPath("$.birthDate", equalTo("1970-03-27")))
                .andExpect(jsonPath("$.grade.id", equalTo(newStudent1.getGrade().getId().intValue())));

    }

    @Test
    void DELETE_ShouldDeleteStudentWithSpecifiedId() throws Exception {

        Student newStudent1 = setupFirstStudent();
        Student newStudent2 = setupSecondStudent();

        //assert that both students are in db before deleting
        assertEquals(studentsRepository.findAll().size(), 2);

        mvc.perform(delete("/students/{id}", newStudent1.getId())
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //assert that deleted student is not in db after deleting

        List<Student> allStudentsAfterDelete = studentsRepository.findAll();
        assertEquals(allStudentsAfterDelete.size(), 1);
        assertFalse(allStudentsAfterDelete.stream().map(Student::getId).toList().contains(newStudent1.getId()));

    }

    private Student setupFirstStudent() {
        NewStudentDto newStudent1 = new NewStudentDto("Брекоткин Дмитрий Владиславович", LocalDate.of(1970, 3, 28), 3L);
        return studentService.addStudent(newStudent1);
    }

    private Student setupSecondStudent() {
        NewStudentDto newStudent2 = new NewStudentDto("Соколов Дмитрий Владимирович", LocalDate.of(1965, 4, 11));
        return studentService.addStudent(newStudent2);
    }
}
