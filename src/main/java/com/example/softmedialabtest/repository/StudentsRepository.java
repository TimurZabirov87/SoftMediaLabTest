package com.example.softmedialabtest.repository;

import com.example.softmedialabtest.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository extends JpaRepository<Student, Long> {
}
