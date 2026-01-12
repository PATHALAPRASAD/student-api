package com.example.student_api.util;

import com.example.student_api.entity.StudentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentUtils {
    public static void resultSetToStudentEntityListMapper(ResultSet rs, List<StudentEntity> studentEntities) throws SQLException {
        while (rs.next()) {
            studentEntities.add(resultSetToStudentEntityMapper(rs));
        }
    }

    public static StudentEntity resultSetToStudentEntityMapper(ResultSet rs) throws SQLException {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(rs.getInt("id"));
        studentEntity.setName(rs.getString("name"));
        studentEntity.setRollNumber(rs.getString("roll_number"));
        studentEntity.setCollege(rs.getString("college"));
        studentEntity.setCourse(rs.getString("course"));
        studentEntity.setBranch(rs.getString("branch"));
        studentEntity.setGrade(rs.getString("grade").charAt(0));
        studentEntity.setAverage(rs.getDouble("average"));
        return  studentEntity;
    }
}
