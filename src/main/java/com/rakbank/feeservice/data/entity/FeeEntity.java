package com.rakbank.feeservice.data.entity;

import com.rakbank.feeservice.data.enums.Currency;
import com.rakbank.feeservice.data.enums.FeeType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "fee")
public class FeeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public FeeEntity(String name, FeeType type, Currency currency, Double amount, ZonedDateTime creationDate, ZonedDateTime dueDate) {
        this.name = name;
        this.type = type;
        this.currency = currency;
        this.amount = amount;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FeeType type;

    @Enumerated(EnumType.STRING)
    @Column(name ="currency")
    private Currency currency;

    @Column(name ="amount")
    private Double amount;

    @Column(name ="creation_date")
    private ZonedDateTime creationDate;

    @Column(name ="due_date")
    private ZonedDateTime dueDate;


}
