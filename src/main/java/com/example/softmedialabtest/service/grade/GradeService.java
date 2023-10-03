package com.example.softmedialabtest.service.grade;

import com.example.softmedialabtest.model.grade.Grade;
import com.example.softmedialabtest.model.grade.dto.UpdateGradeRequest;

import java.util.Arrays;
import java.util.List;

public interface GradeService {
    List<Grade> findAll();

    Grade findById(Long id);

    Grade updateGrade(Long id, UpdateGradeRequest updatedGrade);
}
