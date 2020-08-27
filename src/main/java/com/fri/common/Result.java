package com.fri.common;


import java.util.Map;

public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Result() {
        code = 0;
        message = "成功";

    }
    public Result(int code){

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Result SUCCESS() {
        return new Result();
    }

    public static String Errors(int code) {
        return "";
    }
}
