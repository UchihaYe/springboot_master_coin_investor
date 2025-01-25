package com.crypto.invest.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    
    private static final Integer SUCCESS_CODE = 200;
    private static final Integer ERROR_CODE = 500;
    
    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public static <T> Result<T> ok() {
        return new Result<>(SUCCESS_CODE, "操作成功", null);
    }
    
    public static <T> Result<T> ok(T data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }
    
    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }
    
    public static <T> Result<T> fail() {
        return new Result<>(ERROR_CODE, "操作失败", null);
    }
    
    public static <T> Result<T> fail(String message) {
        return new Result<>(ERROR_CODE, message, null);
    }
    
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }
} 