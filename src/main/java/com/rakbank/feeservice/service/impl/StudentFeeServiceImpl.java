package com.rakbank.feeservice.service.impl;

import com.rakbank.feeservice.data.dto.*;

import com.rakbank.feeservice.data.entity.StudentFeeEntity;
import com.rakbank.feeservice.data.enums.FeeStatus;
import com.rakbank.feeservice.data.mapper.StudentFeeMapper;
import com.rakbank.feeservice.repository.FeeRepository;
import com.rakbank.feeservice.repository.StudentFeeRepository;
import com.rakbank.feeservice.service.StudentFeeService;
import com.rakbank.feeservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@RequiredArgsConstructor
@Service
public class StudentFeeServiceImpl implements StudentFeeService {

    private final StudentFeeRepository studentFeeRepository;

    private final FeeRepository feeRepository;

    private final StudentService studentService;

    private final StudentFeeMapper studentFeeMapper;

    @Override
    public List<StudentFee> assignToAllStudentsByGrade(GradeLevelFeeRequest gradeLevelFeeRequestDto, String grade) {

        List<Student> studentDtoList = studentService.getStudentsByGrade(grade);

        List<StudentFeeEntity> studentFeeList = studentDtoList.stream().map(student -> {
            Fee feeDto = feeRepository.getFeeById(gradeLevelFeeRequestDto.getFeeId());
            return new StudentFeeEntity(student.getId(), feeDto.getId(), FeeStatus.PENDING, feeDto.getAmount(), ZonedDateTime.now());
        }).toList();
        List<StudentFeeEntity> result = studentFeeRepository.saveAllAndFlush(studentFeeList);
        return studentFeeMapper.mapToStudentFeeDto(result);
    }

    @Override
    public StudentFee create(StudentFeeRequestDto studentFeeRequestDto) {

        Fee feeDto = feeRepository.getFeeById(studentFeeRequestDto.getFeeId());
        StudentFeeEntity studentFee = new StudentFeeEntity(studentFeeRequestDto.getStudentId(), studentFeeRequestDto.getFeeId(), FeeStatus.PENDING, feeDto.getAmount(), ZonedDateTime.now());
        StudentFeeEntity result = studentFeeRepository.saveAndFlush(studentFee);
        return studentFeeRepository.getStudentFeeById(result.getId());

    }

    @Override
    public List<StudentFee> findAll() {
        return studentFeeRepository.getAllStudentFees();
    }

    @Override
    public List<StudentFee> findAllByStudentId(Long studentId, Boolean isPaid) {
        if(isPaid){
            return studentFeeRepository.getAllStudentFeesStudentIdAndIsStatus(studentId,FeeStatus.FULLY_PAID);
        }else{
            return studentFeeRepository.getAllStudentFeesStudentIdAndStatusIsNot(studentId,FeeStatus.FULLY_PAID);
        }

    }

    @Override
    public Optional<StudentFee> findById(Long id) {
        return Optional.ofNullable(studentFeeRepository.getStudentFeeById(id));
    }

    @Override
    public List<StudentFee> updateStudentStatus(StudentFeeStatusUpdateRequest request) {

        AtomicReference<Double> balanceAmount= new AtomicReference<>(request.getPaidAmount());
            request.getStudentFees().forEach(sf -> {
                studentFeeRepository.findById(sf.getStudentFeeId()).ifPresent(sfee -> {
                    if(sfee.getPendingAmount()<=balanceAmount.get()) {
                        studentFeeRepository.updateStudentFeeStatus(FeeStatus.FULLY_PAID, 0.0, sfee.getPendingAmount(),ZonedDateTime.now(), sfee.getId());
                        balanceAmount.updateAndGet(bal->bal-sfee.getPendingAmount());
                    }else if(balanceAmount.get() >0.0){
                        Double pendingAmount=sfee.getPendingAmount()-balanceAmount.get();
                        Double paidAmount=balanceAmount.get();
                        studentFeeRepository.updateStudentFeeStatus(FeeStatus.PARTIALLY_PAID, pendingAmount, paidAmount, ZonedDateTime.now(), sfee.getId());
                        balanceAmount.updateAndGet(bal->0.0);
                    }
                });
            });


        return request.getStudentFees().stream().map(sf->studentFeeRepository.getStudentFeeById( sf.getStudentFeeId())).toList();
    }


}
