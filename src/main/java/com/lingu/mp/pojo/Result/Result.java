package com.lingu.mp.pojo.Result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 与前端交互的数据结构
 *
 * @param <T> 携带数据的类型
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private Integer code;   //状态码
    private String message; //返回信息

    private T data;         //返回数据


    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }


    public static <T> Result<T> error() {
        return new Result<>(501, "error", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(501, message, null);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}