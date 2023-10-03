package com.example.softmedialabtest.model.grade.dto;

import com.example.softmedialabtest.model.grade.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeDto {
    private Long id;
    private String text;

    public static GradeDto fromGrade(Grade grade) {
        return new GradeDto(grade.getId(), grade.getText());
    }
}
