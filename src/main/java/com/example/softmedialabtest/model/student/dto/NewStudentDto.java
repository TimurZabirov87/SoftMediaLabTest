package com.example.softmedialabtest.model.student.dto;

import com.example.softmedialabtest.validation.ValidStudentGrade;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class NewStudentDto {
    @Size(min = 3, max = 150)
    @NonNull
    private String fullName;
    @Past
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthDate;
    @ValidStudentGrade
    private Long gradeId;
}
