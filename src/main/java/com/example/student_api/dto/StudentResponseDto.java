package com.example.student_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
    private int id;
    private String name;
    private String rollNumber;
    private String course;
    private String branch;
    private String college;
    private double average;
    private char grade;
}
