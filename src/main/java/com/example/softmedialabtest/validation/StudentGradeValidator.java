package com.example.softmedialabtest.validation;

import com.example.softmedialabtest.model.grade.StudentGradeEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.function.Predicate;


public class StudentGradeValidator implements ConstraintValidator<ValidStudentGrade, String> {

    public void initialize(ValidStudentGrade constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {

        return Arrays.stream(StudentGradeEnum.values()).toList().stream()
                .map(StudentGradeEnum::getTranslation)
                .anyMatch(Predicate.isEqual(value));

    }
}