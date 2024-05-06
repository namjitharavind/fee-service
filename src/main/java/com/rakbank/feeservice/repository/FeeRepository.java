package com.rakbank.feeservice.repository;



import com.rakbank.feeservice.data.dto.Fee;
import com.rakbank.feeservice.data.entity.FeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeeRepository extends JpaRepository<FeeEntity, Long> {

    @Query("Select new com.rakbank.feeservice.data.dto.Fee(" +
            "f.id," +
            "f.name," +
            "f.type," +
            "f.currency," +
            "f.amount," +
            "f.creationDate," +
            "f.dueDate" +
            ") from FeeEntity f")
    List<Fee> getAllFees();

    @Query("Select new com.rakbank.feeservice.data.dto.Fee(" +
            "f.id," +
            "f.name," +
            "f.type," +
            "f.currency," +
            "f.amount," +
            "f.creationDate," +
            "f.dueDate" +
            ") from FeeEntity f where f.id = :id")
    Fee getFeeById(@Param("id") Long id);
}