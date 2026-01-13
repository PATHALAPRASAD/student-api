package com.example.student_api.repository.implmentations;

import com.example.student_api.dto.AddStudentRequestDto;
import com.example.student_api.dto.UpdateStudentRequestDto;
import com.example.student_api.entity.StudentEntity;
import com.example.student_api.exception.StudentNotFoundException;
import com.example.student_api.repository.interfaces.JdbcStudentRepository;
import com.example.student_api.util.StudentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcStudentRepositoryImpl implements JdbcStudentRepository {

    private final DataSource dataSource;

    @Autowired
    Environment environment;

    // Constructor injection of the DataSource bean
    public JdbcStudentRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<StudentEntity> getAllStudents() {
        List<StudentEntity> studentEntities = new ArrayList<>();

        // Manually manage Connection, Statement, and ResultSet resources
        String query = "SELECT * FROM student";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            StudentUtils.resultSetToStudentEntityListMapper(rs, studentEntities);

        } catch (SQLException e) {
            // In a real application, log the exception and handle it appropriately
//            e.printStackTrace();
            throw new RuntimeException("Error fetching all students\t\t:\t" + e.getMessage());
        }
        return studentEntities;
    }

    @Override
    public StudentEntity getStudentById(int id) {
        StudentEntity studentEntity;
        String query = "SELECT * FROM student WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            studentEntity = StudentUtils.resultSetToStudentEntityMapper(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching student by id\t\t:\t" + e.getMessage());
        }
        return studentEntity;
    }

    @Override
    public List<StudentEntity> getStudentsByAverageGreaterThan(double average) {
        List<StudentEntity> studentEntities = new ArrayList<>();
        String query = "SELECT * FROM student WHERE average > ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, average);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) studentEntities.add(StudentUtils.resultSetToStudentEntityMapper(rs));

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching student by id\t\t:\t" + e.getMessage());
        }
        return studentEntities;
    }

    @Override
    public StudentEntity getStudentByRollNumber(String rollNumber) throws StudentNotFoundException {
        StudentEntity studentEntity;
        String query = "SELECT * FROM student WHERE roll_number=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, rollNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                studentEntity = StudentUtils.resultSetToStudentEntityMapper(rs);
            else
                throw new StudentNotFoundException(environment.getProperty("STUDENT_NOT_FOUND"));
        } catch (SQLException | StudentNotFoundException e) {
            if (e instanceof StudentNotFoundException)
                throw new StudentNotFoundException(environment.getProperty("STUDENT_NOT_FOUND"));

            throw new RuntimeException("Error fetching student by id\t\t:\t" + e.getMessage());
        }
        return studentEntity;
    }

    @Override
    public int addStudent(AddStudentRequestDto addStudentRequestDto) {
        int newStudentId = -1;
        String query = "INSERT INTO student (name, roll_number, college, course, branch, average, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, addStudentRequestDto.getName());
            pstmt.setString(2, addStudentRequestDto.getRollNumber());
            pstmt.setString(3, addStudentRequestDto.getCollege());
            pstmt.setString(4, addStudentRequestDto.getCourse());
            pstmt.setString(5, addStudentRequestDto.getBranch());
            pstmt.setDouble(6, addStudentRequestDto.getAverage());
            pstmt.setString(7, String.valueOf(addStudentRequestDto.getGrade()));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    newStudentId = rs.getInt(1);
                }
            }

//            int res = pstmt.executeUpdate();
//            System.out.println("response = " + res);
        } catch (SQLException e) {
            System.out.println(e.toString());
            throw new RuntimeException("Error adding student\t\t:\t" + e.getMessage());
        }
        return newStudentId;
    }

    @Override
    public int updateStudent(UpdateStudentRequestDto updateStudentRequestDto) {
        int affectedRows;
        String query = "UPDATE student SET name=?, roll_number=?, college=?, course=?, branch=?, average=?, grade=? WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, updateStudentRequestDto.getName());
            pstmt.setString(2, updateStudentRequestDto.getRollNumber());
            pstmt.setString(3, updateStudentRequestDto.getCollege());
            pstmt.setString(4, updateStudentRequestDto.getCourse());
            pstmt.setString(5, updateStudentRequestDto.getBranch());
            pstmt.setDouble(6, updateStudentRequestDto.getAverage());
            pstmt.setString(7, String.valueOf(updateStudentRequestDto.getGrade()));
            pstmt.setInt(8, updateStudentRequestDto.getId());

            affectedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
            throw new RuntimeException("Error updating student\t\t:\t" + e.getMessage());
        }
        System.out.println("affectedRows : " + affectedRows);
        return affectedRows;
    }

    @Override
    public int updateCollege(int id, String college) {
        int affectedRows;
        String query = "UPDATE student SET college=? WHERE id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, college);
            pstmt.setInt(2, id);

            affectedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.toString());
            throw new RuntimeException("Error updating student\t\t:\t" + e.getMessage());
        }
        System.out.println("updateCollege affectedRows : " + affectedRows);
        return affectedRows;
    }
}