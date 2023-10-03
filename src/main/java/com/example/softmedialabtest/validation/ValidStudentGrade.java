package com.example.softmedialabtest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentGradeValidator.class)
@Documented
public @interface ValidStudentGrade {

    String message() default "Text is invalid: cannot be empty or blank";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
