package com.rakbank.feeservice.service;

import com.rakbank.feeservice.data.dto.Fee;
import com.rakbank.feeservice.data.dto.FeeRequest;

import java.util.List;
import java.util.Optional;

public interface FeeService {

    Fee create(FeeRequest fee);

    List<Fee> findAll();

    Optional<Fee> findById(Long id);
}
