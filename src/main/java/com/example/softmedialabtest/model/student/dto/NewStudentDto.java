package com.example.softmedialabtest.model.student.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class NewStudentDto {
    @Size(min = 3, max = 150)
    @NonNull
    private String fullName;
    @Past
    @NonNull
    private LocalDate birthDate;
}
