package com.rakbank.feeservice.data.mapper;

import com.rakbank.feeservice.data.dto.StudentFee;
import com.rakbank.feeservice.data.dto.StudentFeeRequestDto;
import com.rakbank.feeservice.data.entity.StudentFeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper(config = CommonMapperConfig.class,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentFeeMapper {
    StudentFeeMapper MAPPER = Mappers.getMapper(StudentFeeMapper.class);

    StudentFeeEntity mapToStudentFeeEntity(StudentFeeRequestDto studentFeeRequestDto);

    List<StudentFeeEntity> mapToProjectSurveyDtoResponseList(List<StudentFeeRequestDto> studentFeeRequestDtos);

    StudentFee mapToStudentFeeDto(StudentFeeEntity studentFee);

    List<StudentFee> mapToStudentFeeDto(List<StudentFeeEntity> studentFeeList);
}
