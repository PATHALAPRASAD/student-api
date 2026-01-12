package com.example.student_api.service.implementations;

import com.example.student_api.dto.AddStudentRequestDto;
import com.example.student_api.dto.StudentResponseDto;
import com.example.student_api.exception.ConflictException;
import com.example.student_api.exception.StudentNotFoundException;
import com.example.student_api.repository.interfaces.JdbcStudentRepository;
import com.example.student_api.service.interfaces.JdbcStudentService;
import com.example.student_api.util.MapperUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:/application.properties")
@Transactional
public class JdbcStudentServiceImpl implements JdbcStudentService {

    @Autowired
    JdbcStudentRepository jdbcStudentRepository;

    @Autowired
    Environment environment;

    @Override
    public List<StudentResponseDto> getAllStudents() {
        return MapperUtils.mapAll(jdbcStudentRepository.getAllStudents(), StudentResponseDto.class);
    }

    @Override
    public StudentResponseDto getStudentById(int id) {
        return MapperUtils.map(jdbcStudentRepository.getStudentById(id), StudentResponseDto.class);
    }

    @Override
    public List<StudentResponseDto> getStudentsByAverageGreaterThan(double average) {
        return MapperUtils.mapAll(jdbcStudentRepository.getStudentsByAverageGreaterThan(average), StudentResponseDto.class);
    }

    @Override
    public StudentResponseDto getStudentByRollNumber(String rollNumber) throws StudentNotFoundException {
        return MapperUtils.map(jdbcStudentRepository.getStudentByRollNumber(rollNumber), StudentResponseDto.class);
    }

    @Override
    public StudentResponseDto addStudent(AddStudentRequestDto addStudentRequestDto) throws ConflictException {
//        return MapperUtils.map(jdbcStudentRepository.addStudent(addStudentRequestDto), StudentResponseDto.class);
        int newStudentId = jdbcStudentRepository.addStudent(addStudentRequestDto);
        if (newStudentId > 0) {
            StudentResponseDto studentResponseDto = MapperUtils.map(addStudentRequestDto, StudentResponseDto.class);
            studentResponseDto.setId(newStudentId);
            return studentResponseDto;
        } else {
            throw new ConflictException("Unable to add student\t\t:\t" + addStudentRequestDto.toString());
        }

    }
}
