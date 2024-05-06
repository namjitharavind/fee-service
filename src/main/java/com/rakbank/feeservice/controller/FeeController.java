package com.rakbank.feeservice.controller;

import com.rakbank.feeservice.core.data.ErrorResponse;
import com.rakbank.feeservice.core.exception.BusinessException;
import com.rakbank.feeservice.data.dto.Fee;
import com.rakbank.feeservice.data.dto.FeeRequest;
import com.rakbank.feeservice.service.FeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.rakbank.feeservice.core.constants.Errors.FEE_NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fee")
@Tag(name = "Fee")
public class FeeController {

    private final FeeService feeService;

    @Operation(
            description = "Create a  Fee",
            tags = {"Fee"}
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
    @PostMapping
    public Fee createFee(@RequestBody FeeRequest fee) {
        return feeService.create(fee);
    }

    @Operation(
            description = "Get all fees",
            tags = {"Fee"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Unexpected error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    }
    )
    @GetMapping
    public List<Fee> getFees() {
        return feeService.findAll();
    }

    @Operation(
            description = "Get Fee By Id",
            tags = {"Fee"}
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
    public Fee getFee(@PathVariable Long id) {
        return feeService.findById(id).orElseThrow(() -> new BusinessException(FEE_NOT_FOUND));
    }

}

