package com.example.student_api.service.interfaces;

import com.example.student_api.dto.AddStudentRequestDto;
import com.example.student_api.dto.StudentResponseDto;
import com.example.student_api.dto.UpdateStudentRequestDto;
import com.example.student_api.exception.ConflictException;
import com.example.student_api.exception.StudentNotFoundException;

import java.util.List;

public interface StudentService {

    List<StudentResponseDto> getStudents();

    StudentResponseDto getStudent(int id) throws StudentNotFoundException;

    StudentResponseDto addStudent(AddStudentRequestDto addStudentRequestDto) throws ConflictException;

    StudentResponseDto updateStudent(UpdateStudentRequestDto updateStudentRequestDto) throws StudentNotFoundException;

    StudentResponseDto updateCollege(int id, String college) throws StudentNotFoundException;

    StudentResponseDto deleteStudent(int id) throws StudentNotFoundException;

    StudentResponseDto deleteStudentByRollNumber(String id) throws StudentNotFoundException;

    List<StudentResponseDto> getStudentsAverageGreaterThan(double average);

}
