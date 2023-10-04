package com.example.softmedialabtest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class StudentGradeValidator implements ConstraintValidator<ValidStudentGrade, Long> {

    public void initialize(ValidStudentGrade constraint) {
    }

    public boolean isValid(Long value, ConstraintValidatorContext context) {

        if (value != null) {
            return (2 <= value) && (value <= 5);
        }
        return true;

    }
}