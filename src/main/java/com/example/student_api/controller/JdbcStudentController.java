package com.example.student_api.controller;

import com.example.student_api.dto.AddStudentRequestDto;
import com.example.student_api.dto.StudentResponseDto;
import com.example.student_api.dto.UpdateStudentRequestDto;
import com.example.student_api.exception.ConflictException;
import com.example.student_api.exception.StudentNotFoundException;
import com.example.student_api.service.interfaces.JdbcStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jdbc-students")
@CrossOrigin("*")
@Tag(name="JDBC Student Services", description = "JDBC - Student API")
@Validated
public class JdbcStudentController {

    @Autowired
    JdbcStudentService jdbcStudentService;

    @GetMapping
    @Operation(summary = "JDBC - Get All Students")
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        List<StudentResponseDto> response = jdbcStudentService.getAllStudents();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "JDBC - Get Student by Id")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable("id") int id) {
        StudentResponseDto response = jdbcStudentService.getStudentById(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/average-greater-than/{average}")
    @Operation(summary = "JDBC - Get All Students whose average greater than entered average")
    public ResponseEntity<List<StudentResponseDto>> getStudentsAverageGreaterThan(@PathVariable("average") double average) {
        List<StudentResponseDto> response = jdbcStudentService.getStudentsByAverageGreaterThan(average);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/rollNumber/{rollNumber}")
    @Operation(summary = "JDBC - Get All Students whose average greater than entered average")
    public ResponseEntity<StudentResponseDto> getStudentByRollNumber(@PathVariable("rollNumber") String rollNumber) throws StudentNotFoundException {
        StudentResponseDto response = jdbcStudentService.getStudentByRollNumber(rollNumber);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping
    @Operation(summary = "JDBC - Add Student")
    public ResponseEntity<StudentResponseDto> addStudent(@Valid @RequestBody AddStudentRequestDto addStudentRequestDto) throws ConflictException {
        StudentResponseDto response = jdbcStudentService.addStudent(addStudentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "JDBC - Update Student")
    public ResponseEntity<StudentResponseDto> updateStudent(@Valid @RequestBody UpdateStudentRequestDto updateStudentRequestDto) {
        StudentResponseDto response = jdbcStudentService.updateStudent(updateStudentRequestDto);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}/{college}")
    @Operation(summary = "JDBC - Update College Field By ID")
    public ResponseEntity<StudentResponseDto> updateCollege(@Positive(message = "ID {should.positive}") @Min(value = 1, message = "User ID must be greater than or equal to 1") @PathVariable("id") Integer id, @PathVariable("college") String college) throws StudentNotFoundException {
        StudentResponseDto response = jdbcStudentService.updateCollege(id, college);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "JDBC - Delete Student")
    public ResponseEntity<StudentResponseDto> deleteStudent(@PathVariable("id") int id) throws StudentNotFoundException, ConflictException {
        StudentResponseDto response = jdbcStudentService.deleteStudent(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/rno/{rno}")
    @Operation(summary = "Delete Student By Roll Number")
    public ResponseEntity<StudentResponseDto> deleteStudentByRollNumber(@PathVariable("rno") String rollNumber) throws StudentNotFoundException, ConflictException {
        StudentResponseDto response = jdbcStudentService.deleteStudent(rollNumber);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}