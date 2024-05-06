package com.rakbank.feeservice.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@ToString
@Getter
@Setter
public class StudentFeeStatusUpdateRequest {
    private Long studentId;
    private Double totalAmount;
    private Double paidAmount;
    List<StudentFeeForUpdate> studentFees;
}
