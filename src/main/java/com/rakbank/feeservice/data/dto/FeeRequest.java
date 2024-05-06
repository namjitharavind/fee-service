package com.rakbank.feeservice.data.dto;


import com.rakbank.feeservice.data.enums.Currency;
import com.rakbank.feeservice.data.enums.FeeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;


@AllArgsConstructor
@Getter
@Setter
public class FeeRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private FeeType type;
    private Currency currency;
    private Double amount;
    private ZonedDateTime creationDate;
    private ZonedDateTime dueDate;

}
