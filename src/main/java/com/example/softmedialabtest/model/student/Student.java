package com.example.softmedialabtest.model.student;

import com.example.softmedialabtest.model.grade.Grade;
import com.example.softmedialabtest.model.student.dto.NewStudentDto;
import com.example.softmedialabtest.model.student.dto.UpdateStudentRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "birthdate", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "student_grades",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "garde_id", referencedColumnName = "id")})
    private Grade grade;

    @CreatedDate
    @Column(name = "created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "updated")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updated;

    public static Student fromNewStudentDto(NewStudentDto newStudentDto) {
        Student student = new Student();
        student.setFullname(newStudentDto.getFullName());
        student.setBirthdate(newStudentDto.getBirthDate());

        return student;
    }

    public static Student fromUpdateStudentDto(Long id, UpdateStudentRequest updatedStudent) {
        Student student = new Student();
        student.setId(id);
        student.setFullname(updatedStudent.getFullName());
        student.setBirthdate(updatedStudent.getBirthDate());

        return student;
    }
}

/*
- ФИО
- дата рождения
- успеваемость (опционально, значение из справочника)
 */