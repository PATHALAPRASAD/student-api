package com.example.student_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", insertable = false, updatable = false, nullable = false)
    @NotNull
    private int id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "roll_number", nullable = false, length = 20)
    private String rollNumber;

    @Column(name = "course", nullable = false, length = 20)
    private String course;

    @Column(name = "branch", nullable = false, length = 20)
    private String branch;

    @Column(name = "college", nullable = false, length = 20)
    private String college;

    @Column(name = "grade", nullable = false, length = 1)
    private char grade;

    @Column(name = "average", nullable = false)
    private double average;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "updated_by")
    private String updatedBy;
}