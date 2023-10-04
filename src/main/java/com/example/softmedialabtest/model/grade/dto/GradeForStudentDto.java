package com.example.softmedialabtest.model.grade.dto;

import com.example.softmedialabtest.model.grade.Grade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GradeForStudentDto {
    private Long id;
    private String text;

    public static GradeForStudentDto fromGrade(Grade grade) {
        GradeForStudentDto dto = new GradeForStudentDto();
        if (grade != null) {
            dto.setId(grade.getId());
            dto.setText(grade.getText());
        }
        return dto;
    }
}
