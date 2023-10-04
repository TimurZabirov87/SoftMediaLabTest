package com.example.softmedialabtest.service.student;

import com.example.softmedialabtest.exceptions.NoSuchStudentException;
import com.example.softmedialabtest.model.grade.Grade;
import com.example.softmedialabtest.model.student.Student;
import com.example.softmedialabtest.model.student.dto.NewStudentDto;
import com.example.softmedialabtest.model.student.dto.UpdateStudentRequest;
import com.example.softmedialabtest.repository.StudentsRepository;
import com.example.softmedialabtest.service.grade.GradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentsRepository studentsRepository;
    private final GradeService gradeService;

    @Override
    public Student findById(Long id) {
        return studentsRepository.findById(id)
                .orElseThrow(() -> new NoSuchStudentException("Student with id: " + id + " not found."));
    }

    @Override
    @Transactional
    public Student addStudent(NewStudentDto newStudent) {

        Grade grade = null;
        if (newStudent.getGradeId() != null) {
            grade = gradeService.findById(newStudent.getGradeId());
        }

        return studentsRepository.save(Student.fromNewStudentDto(newStudent, grade));

    }

    @Override
    @Transactional
    public Student updateStudent(Long id, UpdateStudentRequest updatedStudent) {
        Student studentForUpdate = studentsRepository.findById(id)
                .orElseThrow(() -> new NoSuchStudentException(
                        "Error updating data. Student with id: " + id + " not found."));
        if (updatedStudent.getFullName() != null) {
            studentForUpdate.setFullname(updatedStudent.getFullName());
        }
        if (updatedStudent.getBirthDate() != null) {
            studentForUpdate.setBirthdate(updatedStudent.getBirthDate());
        }
        if (updatedStudent.getGradeId() != null) {
            studentForUpdate.setGrade(gradeService.findById(updatedStudent.getGradeId()));
        }
        studentForUpdate.setUpdated(LocalDateTime.now());
        return studentsRepository.save(studentForUpdate);
    }

    @Override
    public List<Student> findAll() {
        return studentsRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student studentForDelete = studentsRepository.findById(id)
                .orElseThrow(() -> new NoSuchStudentException(
                        "Error deleting data. Student with id: " + id + " not found."));
        studentsRepository.deleteById(id);
    }
}
