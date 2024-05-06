package com.rakbank.feeservice.controller;

import com.rakbank.feeservice.core.data.ErrorResponse;
import com.rakbank.feeservice.core.exception.BusinessException;
import com.rakbank.feeservice.data.dto.GradeLevelFeeRequest;
import com.rakbank.feeservice.data.dto.StudentFee;
import com.rakbank.feeservice.data.dto.StudentFeeRequestDto;
import com.rakbank.feeservice.data.dto.StudentFeeStatusUpdateRequest;
import com.rakbank.feeservice.service.StudentFeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.rakbank.feeservice.core.constants.Errors.STUDENT_FEE_NOT_FOUND;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/student-fee")
@Tag(name = "Student Fee")
public class StudentFeeController {

    private final StudentFeeService studentFeeService;

    @Operation(
            description = "Create a Student Fee",
            tags = {"Student Fee"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    }
    )
    @PostMapping()
    public StudentFee create(@RequestBody StudentFeeRequestDto studentFeeRequestDto) {
        return studentFeeService.create(studentFeeRequestDto);
    }


    @Operation(
            description = "Assign a Student Fee to all students in a grade",
            tags = {"Student Fee"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StudentFee.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    }
    )
    @PostMapping(path = "/assign-by-grade/{grade}")
    public List<StudentFee> assignToAllStudentsByGrade(@RequestBody GradeLevelFeeRequest gradeLevelFeeRequestDto, @PathVariable String grade) {
        return studentFeeService.assignToAllStudentsByGrade(gradeLevelFeeRequestDto, grade);
    }

    @Operation(
            description = "Get Student Fee By student Id and status",
            tags = {"Student Fee"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    }
    )
    @GetMapping
    public List<StudentFee> getStudentFees(@RequestParam(required = false) Long studentId, @RequestParam(required = false, defaultValue = "false") Boolean isPaid) {
        List<StudentFee> studentFeeList = new ArrayList<>();
        if (studentId != null && studentId != 0) {
            studentFeeList = studentFeeService.findAllByStudentId(studentId, isPaid);
        } else {
            studentFeeList = studentFeeService.findAll();
        }
        return studentFeeList;
    }


    @Operation(
            description = "Get Student Fee By Id",
            tags = {"Student Fee"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    }
    )
    @GetMapping(path = "/{id}")
    public StudentFee getStudentFee(@PathVariable Long id) {
        return studentFeeService.findById(id).orElseThrow(() -> new BusinessException(STUDENT_FEE_NOT_FOUND));
    }


    @Operation(
            description = "Update the status of the Student fees",
            tags = {"Student Fee"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    }
    )
    @PostMapping(path = "/status-update")
    public List<StudentFee> updateStatus(@RequestBody StudentFeeStatusUpdateRequest request) {
        log.info("Fee update call received in status-update with request {}", request);
        return studentFeeService.updateStudentStatus(request);
    }
}