package com.example.softmedialabtest.repository;

import com.example.softmedialabtest.model.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradesRepository extends JpaRepository<Grade, Long> {
}
