package com.example.softmedialabtest.model.student.dto;

import com.example.softmedialabtest.model.grade.dto.GradeForStudentDto;
import com.example.softmedialabtest.model.student.Student;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithIdDto {
    @NonNull
    private Long id;
    @NonNull
    @Size(min = 3, max = 150)
    private String fullName;
    @NonNull
    @Past
    private LocalDate birthDate;
    private GradeForStudentDto grade;


    public static StudentWithIdDto fromStudent(Student student) {
        return new StudentWithIdDto(student.getId(), student.getFullname(), student.getBirthdate(), GradeForStudentDto.fromGrade(student.getGrade()));
    }

}
