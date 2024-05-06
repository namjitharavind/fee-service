package com.rakbank.feeservice.data.entity;

import com.rakbank.feeservice.data.enums.FeeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "student_fee")
@NoArgsConstructor
public class StudentFeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "fee_id")
    private Long feeId;

    @Enumerated(EnumType.STRING)
    @Column(name ="status")
    private FeeStatus status;

    @Column(name ="pending_amount")
    private Double pendingAmount;

    @Column(name ="paid_amount")
    private Double paidAmount;

    @Column(name ="paid_date")
    private ZonedDateTime paidDate;

    @Column(name ="creation_date")
    private ZonedDateTime creationDate;

    public StudentFeeEntity(Long studentId, Long feeId, FeeStatus status, Double pendingAmount, ZonedDateTime creationDate) {
        this.studentId = studentId;
        this.feeId = feeId;
        this.status = status;
        this.pendingAmount = pendingAmount;
        this.creationDate = creationDate;
    }
}
