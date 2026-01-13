package com.example.student_api.service.interfaces;

import com.example.student_api.dto.AddStudentRequestDto;
import com.example.student_api.dto.StudentResponseDto;
import com.example.student_api.dto.UpdateStudentRequestDto;
import com.example.student_api.exception.ConflictException;
import com.example.student_api.exception.StudentNotFoundException;

import java.util.List;

public interface JdbcStudentService {
    List<StudentResponseDto> getAllStudents();
    StudentResponseDto getStudentById(int id);
    List<StudentResponseDto> getStudentsByAverageGreaterThan(double average);
    StudentResponseDto getStudentByRollNumber(String rollNumber) throws StudentNotFoundException;
    StudentResponseDto addStudent(AddStudentRequestDto addStudentRequestDto) throws ConflictException;
    StudentResponseDto updateStudent(UpdateStudentRequestDto updateStudentRequestDto);
    StudentResponseDto updateCollege(int id, String college) throws StudentNotFoundException;
    StudentResponseDto deleteStudent(int id) throws StudentNotFoundException, ConflictException;
    StudentResponseDto deleteStudent(String rollNumber) throws StudentNotFoundException, ConflictException;
}
