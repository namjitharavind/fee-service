package com.rakbank.feeservice.core.exception;

import com.rakbank.feeservice.core.data.Error;
import com.rakbank.feeservice.core.constants.Errors;
import com.rakbank.feeservice.core.data.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        List<Error> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    Errors errors = new Errors(error.getDefaultMessage());
                    return new Error(errors.getCode(), errors.getMessage(), error.getField());
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse(validationErrors, request.getDescription(false)), status);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.error("1004", ex.getMessage().contains(":") ? ex.getMessage().split(":")[0] : ex.getMessage(), ex);
        Error errorDto = new Error("1004", ex.getMessage().contains(":") ? ex.getMessage().split(":")[0] : ex.getMessage());
        List<Error> errorDtos = new ArrayList<>();
        errorDtos.add(errorDto);
        return new ResponseEntity<>(new ErrorResponse(errorDtos, request.getDescription(false)), status);
    }


    /**
     * All the exception which is not handled by developer code will reach here.
     *
     * @return 500 Internal server.
     */
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> defaultErrorHandler(
            Exception ex,
            WebRequest request) {
        Error errorDto = new Error("1000", "Unknown Exception");
        List<Error> errorDtos = new ArrayList<>();
        errorDtos.add(errorDto);
        log.error("Error code 1000 Unknown Exception: ", ex);
        return new ResponseEntity<>(new ErrorResponse(errorDtos, request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        Error errorDto = new Error("1005", error);
        List<Error> errorDtos = new ArrayList<>();
        errorDtos.add(errorDto);
        return new ResponseEntity<>(new ErrorResponse(errorDtos, request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BusinessException.class})
    protected ResponseEntity defaultErrorHandler(
            BusinessException ex, WebRequest request) {
        Error errorDto = new Error(ex.getCode(), ex.getMessage(), ex.getField());
        List<Error> errorDtos = new ArrayList<>();
        errorDtos.add(errorDto);
        return new ResponseEntity<>(new ErrorResponse(errorDtos, request.getDescription(false)), ex.getStatus());
    }
}
