package com.example.softmedialabtest.service.grade;

import com.example.softmedialabtest.exceptions.NoSuchGradeException;
import com.example.softmedialabtest.model.grade.Grade;
import com.example.softmedialabtest.model.grade.dto.UpdateGradeRequest;
import com.example.softmedialabtest.repository.GradesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GradeServiceImpl implements GradeService {

    private final GradesRepository gradesRepository;

    @Override
    public List<Grade> findAll() {
        return gradesRepository.findAll();
    }

    @Override
    public Grade findById(Long id) {
        return gradesRepository.findById(id)
                .orElseThrow(() -> new NoSuchGradeException("Grade with id: " + id + " not found."));
    }

    @Override
    public Grade updateGrade(Long id, UpdateGradeRequest updatedGrade) {
        Grade gradeToUpdate = gradesRepository.findById(id)
                .orElseThrow(() -> new NoSuchGradeException("Error updating data. Grade with id: " + id + " not found."));
        gradeToUpdate.setText(updatedGrade.getText());

        return gradesRepository.save(gradeToUpdate);

    }
}