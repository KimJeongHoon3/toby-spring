package com.toby.spring.controller;

public class ErrorVo {
    String code;
    String message;

    public ErrorVo(String code, String message) {
        this.code = code;
        this.message = message;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
