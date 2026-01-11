package com.example.student_api.controller;

import com.example.student_api.dto.AddStudentRequestDto;
import com.example.student_api.dto.StudentResponseDto;
import com.example.student_api.dto.UpdateStudentRequestDto;
import com.example.student_api.exception.ConflictException;
import com.example.student_api.exception.StudentNotFoundException;
import com.example.student_api.service.interfaces.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")
@Tag(name="Student Services", description = "Student API")
@Validated
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Student by Id")
    public ResponseEntity<StudentResponseDto> getStudent(@PathVariable("id") int id) throws StudentNotFoundException {
        StudentResponseDto response = studentService.getStudent(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("")
    @Operation(summary = "Get All Students")
    public ResponseEntity<List<StudentResponseDto>> getStudents() {
        List<StudentResponseDto> response = studentService.getStudents();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Student")
    public ResponseEntity<StudentResponseDto> deleteStudent(@PathVariable("id") int id) throws StudentNotFoundException {
        StudentResponseDto response = studentService.deleteStudent(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteByRollNumber/{rollNumber}")
    @Operation(summary = "Delete Student By Roll Number")
    public ResponseEntity<StudentResponseDto> deleteStudentByRollNumber(@PathVariable("rollNumber") String rollNumber) throws StudentNotFoundException {
        StudentResponseDto response = studentService.deleteStudentByRollNumber(rollNumber);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    @Operation(summary = "Add Student")
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody AddStudentRequestDto addStudentRequestDto) throws ConflictException {
        StudentResponseDto response = studentService.addStudent(addStudentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Student")
    public ResponseEntity<StudentResponseDto> updateStudent(@RequestBody UpdateStudentRequestDto updateStudentRequestDto) throws StudentNotFoundException {
        StudentResponseDto response = studentService.updateStudent(updateStudentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}/{college}")
    @Operation(summary = "Update College Field By ID")
    public ResponseEntity<StudentResponseDto> updateCollege(@Positive @PathVariable("id") int id, @PathVariable("college") String college) throws StudentNotFoundException {
        StudentResponseDto response = studentService.updateCollege(id, college);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/average-greater-than/{average}")
    @Operation(summary = "Get All Students whose average greater than entered average")
    public ResponseEntity<List<StudentResponseDto>> getStudentsAverageGreaterThan(@PathVariable("average") double average) {
        List<StudentResponseDto> response = studentService.getStudentsAverageGreaterThan(average);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}