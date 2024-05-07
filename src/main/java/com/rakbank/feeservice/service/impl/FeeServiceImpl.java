package com.rakbank.feeservice.service.impl;

import com.rakbank.feeservice.data.dto.Fee;
import com.rakbank.feeservice.data.dto.FeeRequest;
import com.rakbank.feeservice.data.entity.FeeEntity;
import com.rakbank.feeservice.repository.FeeRepository;
import com.rakbank.feeservice.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;

    @Override
    public Fee create(FeeRequest fee) {
        FeeEntity result = feeRepository.saveAndFlush(new FeeEntity(fee.getName(),fee.getType(),fee.getCurrency(),fee.getAmount(), ZonedDateTime.now(),ZonedDateTime.now()));
        return feeRepository.getFeeById(result.getId());
    }

    @Override
    public List<Fee> findAll() {
        return feeRepository.getAllFees();
    }

    @Override
    public Optional<Fee> findById(Long id) {
        return Optional.ofNullable(feeRepository.getFeeById(id));
    }
}
