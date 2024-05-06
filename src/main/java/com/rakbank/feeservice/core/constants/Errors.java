package com.rakbank.feeservice.core.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Errors {

    public final static String STUDENT_NOT_FOUND = "1001:404:Student not found.";
    public final static String FEE_NOT_FOUND = "1002:404:Fee not found.";
    public final static String STUDENT_FEE_NOT_FOUND = "1003:404:Student Fee not found.";

    private final String code;
    private final HttpStatus status;
    private final String message;

    public Errors(String messageTemplate) {
        String[] fields = messageTemplate.split(":");
        this.code = fields[0];
        this.status = HttpStatus.valueOf(Integer.parseInt(fields[1]));
        this.message = fields[2];
    }


}
