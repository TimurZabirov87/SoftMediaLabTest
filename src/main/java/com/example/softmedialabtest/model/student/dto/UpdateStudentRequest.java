package com.example.softmedialabtest.model.student.dto;

import com.example.softmedialabtest.validation.ValidStudentGrade;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateStudentRequest {
    @Size(min = 3, max = 150)
    private String fullName;
    @Past
    private LocalDate birthDate;
    @ValidStudentGrade
    private Long gradeId;
}
