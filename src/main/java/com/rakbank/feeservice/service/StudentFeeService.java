package com.rakbank.feeservice.service;

import com.rakbank.feeservice.data.dto.GradeLevelFeeRequest;
import com.rakbank.feeservice.data.dto.StudentFee;
import com.rakbank.feeservice.data.dto.StudentFeeRequestDto;
import com.rakbank.feeservice.data.dto.StudentFeeStatusUpdateRequest;


import java.util.List;
import java.util.Optional;

public interface StudentFeeService {

    List<StudentFee> assignToAllStudentsByGrade(GradeLevelFeeRequest gradeLevelFeeRequestDto, String grade);

    StudentFee create(StudentFeeRequestDto studentFeeRequestDto);

    List<StudentFee> findAll();

    List<StudentFee> findAllByStudentId(Long studentId, Boolean isPaid);

    Optional<StudentFee> findById(Long id);

    List<StudentFee> updateStudentStatus(StudentFeeStatusUpdateRequest request);
}
