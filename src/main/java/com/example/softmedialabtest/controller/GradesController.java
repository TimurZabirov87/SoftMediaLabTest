package com.example.softmedialabtest.controller;

import com.example.softmedialabtest.model.grade.Grade;
import com.example.softmedialabtest.model.grade.dto.GradeDto;
import com.example.softmedialabtest.model.grade.dto.UpdateGradeRequest;
import com.example.softmedialabtest.service.grade.GradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grades")
@Slf4j
public class GradesController {


    private final GradeService gradeService;

    @Autowired
    public GradesController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    /**
     * Метод позволяет получить список всех оценок успеваемости (элементов класса {@link Grade}), приведенных к {@link GradeDto}.
     */
    @GetMapping
    public List<GradeDto> getAllGrades() {
        log.info("Получен get запрос к эндпоинту: /grades");

        List<GradeDto> grades = gradeService.findAll().stream()
                .map(GradeDto::fromGrade)
                .collect(Collectors.toList());

        return grades;
    }

    /**
     * Метод позволяет получить оценку успеваемости (элемент класса {@link GradeDto}) по {@code id}</b>
     *
     * @param id Идентификатор искомого в Хранилище элемента класса {@link Grade}.
     * @return Найденный по заданным параметрам элемент, приведенный к {@link GradeDto}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GradeDto> getGradeById(@PathVariable(name = "id") Long id) {
        log.info("Получен get запрос к эндпоинту: /grades/{id}  id = {}", id);

        Grade grade = gradeService.findById(id);

        return new ResponseEntity<>(GradeDto.fromGrade(grade), HttpStatus.OK);
    }

    /**
     * Метод позволяет получить изменить текстовое описание оценки успеваемости с переданным id.
     *
     * @param updatedGrade Обновляемый элемент класса {@link UpdateGradeRequest}
     * @return Обновленный элемент, приведенный к {@link GradeDto}.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GradeDto> updateGrade(@PathVariable(name = "id") Long id,
                                                @RequestBody UpdateGradeRequest updatedGrade) {
        log.info("Получен patch запрос к эндпоинту: /grades/{id} id = {}, \n {}", id, updatedGrade);

        Grade grade = gradeService.updateGrade(id, updatedGrade);

        return new ResponseEntity<>(GradeDto.fromGrade(grade), HttpStatus.OK);
    }
}
