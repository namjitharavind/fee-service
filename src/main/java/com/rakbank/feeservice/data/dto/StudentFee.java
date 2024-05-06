package com.rakbank.feeservice.data.dto;

import com.rakbank.feeservice.data.enums.Currency;
import com.rakbank.feeservice.data.enums.FeeStatus;
import com.rakbank.feeservice.data.enums.FeeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
public class StudentFee {
    private Long id;
    private Long studentId;
    private Long feeId;
    private FeeStatus status;
    private Double amount;
    private Double paidAmount;
    private ZonedDateTime paidDate;
    private ZonedDateTime creationDate;
    private String name;
    private FeeType type;
    private Currency currency;
    private ZonedDateTime dueDate;

}
