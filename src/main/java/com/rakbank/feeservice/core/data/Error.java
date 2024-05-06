package com.rakbank.feeservice.core.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private String code;
    private String msg;
    private String field;

    public Error(String code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public Error(String msg){
        this.msg=msg;
    }
}
