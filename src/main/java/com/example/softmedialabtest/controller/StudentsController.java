package com.example.softmedialabtest.controller;

import com.example.softmedialabtest.model.student.Student;
import com.example.softmedialabtest.model.student.dto.NewStudentDto;
import com.example.softmedialabtest.model.student.dto.StudentDto;
import com.example.softmedialabtest.model.student.dto.UpdateStudentRequest;
import com.example.softmedialabtest.service.student.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentsController {

    private final StudentService studentService;

    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Метод позволяет получить список всех студентов (элементов класса {@link Student}), приведенных к {@link StudentDto}.
     *
     * @return Список всех хранящихся элементов, приведенных к {@link StudentDto}.
     */
    @GetMapping
    public List<StudentDto> getAllStudents() {
        log.info("Получен get запрос к эндпоинту: /students");

        List<StudentDto> students = studentService.findAll().stream()
                .map(StudentDto::fromStudent)
                .collect(Collectors.toList());

        return students;
    }

    /**
     * Метод позволяет получить студента (элемент класса {@link StudentDto}) по {@code id}</b>
     *
     * @param id Идентификатор искомого в Хранилище элемента класса {@link Student}.
     * @return Найденный по заданным параметрам элемент, приведенный к {@link StudentDto}.
     */
    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable(name = "id") Long id) {
        log.info("Получен get запрос к эндпоинту: /students/{id}  id = {}", id);

        Student student = studentService.findById(id);

        return StudentDto.fromStudent(student);
    }

    /**
     * Метод позволяет добавить студента с переданными данными.
     *
     * @param newStudent Добавляемый элемент класса {@link NewStudentDto}
     * @return Добавленный элемент, приведенный к {@link StudentDto}.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto addStudent(@RequestBody @Valid NewStudentDto newStudent) {
        log.info("Получен post запрос к эндпоинту: /students \n {}", newStudent);

        Student student = studentService.addStudent(newStudent);

        return StudentDto.fromStudent(student);
    }

    /**
     * Метод позволяет обновить данные студента с переданным id.
     *
     * @param updatedStudent Обновляемый элемент класса {@link UpdateStudentRequest}
     * @return Обновленный элемент, приведенный к {@link StudentDto}.
     */
    @PatchMapping("/{id}")
    public StudentDto updateStudent(@PathVariable(name = "id") Long id,
                                    @RequestBody @Valid UpdateStudentRequest updatedStudent) {
        log.info("Получен patch запрос к эндпоинту: /students/{id} id = {}, \n {}", id, updatedStudent);

        Student student = studentService.updateStudent(id, updatedStudent);

        return StudentDto.fromStudent(student);
    }

    /**
     * Метод позволяет удалить студента с переданным id.
     *
     * @param id Идентификатор искомого в Хранилище элемента класса {@link Student}.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable(name = "id") Long id) {
        log.info("Получен delete запрос к эндпоинту: /students/{id} id = {}, \n {}", id);

        studentService.deleteStudent(id);
    }
}
