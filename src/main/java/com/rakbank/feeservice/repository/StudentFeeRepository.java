package com.rakbank.feeservice.repository;


import com.rakbank.feeservice.data.dto.StudentFee;
import com.rakbank.feeservice.data.entity.StudentFeeEntity;
import com.rakbank.feeservice.data.enums.FeeStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface StudentFeeRepository extends JpaRepository<StudentFeeEntity, Long> {


    @Query("SELECT new com.rakbank.feeservice.data.dto.StudentFee(" +
            "sf.id," +
            "sf.studentId," +
            "sf.feeId," +
            "sf.status," +
            "f.amount," +
            "sf.paidAmount," +
            "sf.paidDate," +
            "sf.creationDate," +
            "f.name," +
            "f.type," +
            "f.currency," +
            "f.dueDate" +
            ") FROM StudentFeeEntity sf" +
            " JOIN FeeEntity f ON f.id=sf.feeId")
    List<StudentFee> getAllStudentFees();

    @Query("SELECT new com.rakbank.feeservice.data.dto.StudentFee(" +
            "sf.id," +
            "sf.studentId," +
            "sf.feeId," +
            "sf.status," +
            "f.amount," +
            "sf.paidAmount," +
            "sf.paidDate," +
            "sf.creationDate," +
            "f.name," +
            "f.type," +
            "f.currency," +
            "f.dueDate" +
            ") FROM StudentFeeEntity sf" +
            " JOIN FeeEntity f ON f.id=sf.feeId" +
            " WHERE sf.studentId = :studentId and sf.status = :status")
    List<StudentFee> getAllStudentFeesStudentIdAndIsStatus(@Param("studentId") Long studentId, @Param("status") FeeStatus status);

    @Query("SELECT new com.rakbank.feeservice.data.dto.StudentFee(" +
            "sf.id," +
            "sf.studentId," +
            "sf.feeId," +
            "sf.status," +
            "f.amount," +
            "sf.paidAmount," +
            "sf.paidDate," +
            "sf.creationDate," +
            "f.name," +
            "f.type," +
            "f.currency," +
            "f.dueDate" +
            ") FROM StudentFeeEntity sf" +
            " JOIN FeeEntity f ON f.id=sf.feeId" +
            " WHERE sf.studentId = :studentId and sf.status != :status")
    List<StudentFee> getAllStudentFeesStudentIdAndStatusIsNot(@Param("studentId") Long studentId, @Param("status") FeeStatus status);


    @Query("SELECT new com.rakbank.feeservice.data.dto.StudentFee(" +
            "sf.id," +
            "sf.studentId," +
            "sf.feeId," +
            "sf.status," +
            "f.amount," +
            "sf.paidAmount," +
            "sf.paidDate," +
            "sf.creationDate," +
            "f.name," +
            "f.type," +
            "f.currency," +
            "f.dueDate" +
            ") FROM StudentFeeEntity sf" +
            " JOIN FeeEntity f ON f.id=sf.feeId" +
            " WHERE sf.id = :id")
    StudentFee getStudentFeeById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE StudentFeeEntity sf SET sf.status = :status , sf.pendingAmount = :pendingAmount,  sf.paidAmount = :paidAmount, sf.paidDate = :paidDate where sf.id = :id")
    void updateStudentFeeStatus(@Param("status") FeeStatus status, @Param("pendingAmount") Double pendingAmount, @Param("paidAmount") Double paidAmount, @Param("paidDate") ZonedDateTime paidDate, @Param("id")  Long id);

}