package com.rakbank.feeservice.service;

import com.rakbank.feeservice.data.dto.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudentsByGrade(String grade);


}
