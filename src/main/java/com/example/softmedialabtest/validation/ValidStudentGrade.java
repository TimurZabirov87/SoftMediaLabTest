package com.example.softmedialabtest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentGradeValidator.class)
@Documented
public @interface ValidStudentGrade {

    String message() default "gradeId is invalid: should be 2 <= grade <= 5";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
