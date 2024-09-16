package com.vti.configuration.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int code;
    private String message;
    // private String detailMessage;
    private Object errors;
    private String moreInformation;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
        // this.detailMessage = detailMessage;
        this.moreInformation = "http://localhost/v1/exception/" + code;
    }

    public ErrorResponse(int code, String message, Object errors) {
        this(code, message);
        this.errors = errors;
    }
}
