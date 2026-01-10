package com.example.student_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateStudentRequestDto {

    private int id;

    @Schema(example = "Prasad")
    @NotBlank(message = "Name {should_not_be_blank}")
    @Pattern(regexp = "^[ a-zA-Z]{1,20}$", message = "Name should not be more than 20 chars")
    private String name;

    private String rollNumber;
    private String course;
    private String branch;
    private String college;
    private char grade;
    private double average;
}
