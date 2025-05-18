package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;

    private String message;

    private T data;

    public static Result success() {
        return new Result(200,"操作成功",null);
    }

    public static Result success(String message) {
        return new Result(200,message,message);
    }

    public static <T> Result<T> success(T data) {
        return new Result(200,"操作成功",data);
    }

    public static Result error() {
        return new Result(500,"系统错误，请联系管理员",null);
    }

    public static Result error(String message) {
        return new Result(500,message,null);
    }

    public static Result error(Integer code, String message) {
        return new Result(code,message,null);
    }

}
