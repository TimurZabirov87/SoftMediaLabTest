package com.example.softmedialabtest.service.student;

import com.example.softmedialabtest.model.student.Student;
import com.example.softmedialabtest.model.student.dto.NewStudentDto;
import com.example.softmedialabtest.model.student.dto.UpdateStudentRequest;

import java.util.List;

public interface StudentService {
    Student findById(Long id);

    Student addStudent(NewStudentDto newStudent);

    Student updateStudent(Long id, UpdateStudentRequest updatedStudent);

    List<Student> findAll();

    void deleteStudent(Long id);
}
