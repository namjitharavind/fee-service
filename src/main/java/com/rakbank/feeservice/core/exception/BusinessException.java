package com.rakbank.feeservice.core.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rakbank.feeservice.core.constants.Errors;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessException extends RuntimeException  {
    private final String code;
    private final HttpStatus status;
    private final String message;
    private final String field;

    public BusinessException(String code, HttpStatus status, String message){
        super(message);
        this.code=code;
        this.status=status;
        this.message=message;
        this.field=null;
    }

    public BusinessException(String code, HttpStatus status, String message, String field){
        super(message);
        this.code=code;
        this.status=status;
        this.message=message;
        this.field=field;
    }

    public BusinessException(String error){
        super(new Errors(error).getMessage());
        Errors errors = new Errors(error);
        this.code=errors.getCode();
        this.status=errors.getStatus();
        this.message=errors.getMessage();
        this.field=null;
    }

    public BusinessException(String error,String field){
        super(new Errors(error).getMessage());
        Errors errors = new Errors(error);
        this.code=errors.getCode();
        this.status=errors.getStatus();
        this.message=errors.getMessage();
        this.field=field;
    }

}
