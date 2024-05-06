package com.rakbank.feeservice.core.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<R> {
    private List<Error> errors;
    private String path;

    public ErrorResponse(List<Error> errors) {
        this.errors = errors;
    }

    public ErrorResponse(List<Error> errors, String path) {
        this.errors = errors;
        this.path = path;
    }

}

