package com.example.student_api.repository.interfaces;

import com.example.student_api.dto.AddStudentRequestDto;
import com.example.student_api.dto.UpdateStudentRequestDto;
import com.example.student_api.entity.StudentEntity;
import com.example.student_api.exception.StudentNotFoundException;

import java.util.List;

public interface JdbcStudentRepository {
    List<StudentEntity> getAllStudents();
    StudentEntity getStudentById(int id);
    List<StudentEntity> getStudentsByAverageGreaterThan(double average);
    StudentEntity getStudentByRollNumber(String rollNumber) throws StudentNotFoundException;
    int addStudent(AddStudentRequestDto addStudentRequestDto);
    int updateStudent(UpdateStudentRequestDto updateStudentRequestDto);
    int updateCollege(int id, String college);
}
