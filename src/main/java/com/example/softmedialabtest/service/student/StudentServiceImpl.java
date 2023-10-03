package com.example.softmedialabtest.service.student;

import com.example.softmedialabtest.exceptions.NoSuchStudentException;
import com.example.softmedialabtest.model.student.Student;
import com.example.softmedialabtest.model.student.dto.NewStudentDto;
import com.example.softmedialabtest.model.student.dto.UpdateStudentRequest;
import com.example.softmedialabtest.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentsRepository studentsRepository;

    @Override
    public Student findById(Long id) {
        return studentsRepository.findById(id)
                .orElseThrow(() -> new NoSuchStudentException("Student with id: " + id + " not found."));
    }

    @Override
    public Student addStudent(NewStudentDto newStudent) {
        return studentsRepository.save(Student.fromNewStudentDto(newStudent));
    }

    @Override
    public Student updateStudent(Long id, UpdateStudentRequest updatedStudent) {
        Student studentForUpdate = studentsRepository.findById(id)
                .orElseThrow(() -> new NoSuchStudentException("Error updating data. Student with id: " + id + " not found."));
        if (updatedStudent.getFullName() != null) {
            studentForUpdate.setFullname(updatedStudent.getFullName());
        }
        if (updatedStudent.getBirthDate() != null) {
            studentForUpdate.setBirthdate(updatedStudent.getBirthDate());
        }
        return studentsRepository.save(studentForUpdate);
    }

    @Override
    public List<Student> findAll() {
        return studentsRepository.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        Student studentForDelete = studentsRepository.findById(id)
                .orElseThrow(() -> new NoSuchStudentException("Error deleting data. Student with id: " + id + " not found."));
        studentsRepository.deleteById(id);
    }
}
