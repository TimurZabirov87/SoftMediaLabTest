package com.example.softmedialabtest.model.student.dto;

import com.example.softmedialabtest.model.grade.Grade;
import com.example.softmedialabtest.model.student.Student;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StudentDto {

    @NonNull
    @Size(min = 3, max = 150)
    private String fullName;
    @NonNull
    @Past
    private LocalDate birthDate;
    private Grade grade;


    public static StudentDto fromStudent(Student student) {
        return new StudentDto(student.getFullname(), student.getBirthdate(), student.getGrade());
    }

}
