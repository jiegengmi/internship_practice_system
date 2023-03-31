package com.ikikyou.practice.utils;

import com.ikikyou.practice.constant.StatusCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一返回结果集
 *
 * @author ikikyou
 */
@Setter
@Getter
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -9150650992545177273L;

    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 描述
     */
    private String message;

    /**
     * 返回对象
     */
    private T data;

    public Result(boolean isSuccess, Integer code, String message, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean isSuccess, Integer code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.isSuccess = true;
        this.code = StatusCode.OK;
        this.message = "操作成功!";
    }

    public static <T> Result<T> ok() {
        return restResult(null, StatusCode.OK, null, true);
    }

    public static <T> Result<T> ok(String msg) {
        return restResult(null, StatusCode.OK, msg, true);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(data, StatusCode.OK, null, true);
    }

    public static <T> Result<T> ok(T data, String msg) {
        return restResult(data, StatusCode.OK, msg, true);
    }

    public static <T> Result<T> fail() {
        return restResult(null, StatusCode.ERROR, null, false);
    }

    public static <T> Result<T> fail(T data) {
        return restResult(data, StatusCode.ERROR, null, false);
    }

    public static <T> Result<T> fail(T data, String msg) {
        return restResult(data, StatusCode.ERROR, msg, false);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return restResult(null, code, msg, false);
    }

    public static <T> Result<T> fail(T data, Integer code, String msg) {
        return restResult(data, code, msg, false);
    }

    public static <T> Result<T> process(Boolean success, String msg) {
        return null != success && !Boolean.FALSE.equals(success) ? ok() : fail(msg);
    }

    public static <T> Result<T> fail(String msg) {
        return restResult(null, StatusCode.ERROR, msg, false);
    }

    public static <T> Result<T> restResult(T data, Integer code, String msg, Boolean isSuccess) {
        Result<T> res = new Result<>();
        res.setCode(code);
        res.setSuccess(isSuccess);
        res.setMessage(msg);
        res.setData(data);
        return res;
    }

    @Override
    public String toString() {
        return "Result(code=" + this.getCode() + ", msg=" + this.getMessage() + ", success=" + this.isSuccess() + ", data=" + this.getData() + ")";
    }


    public Result(int code, String message, Boolean isSuccess, T data) {
        this.code = code;
        this.message = message;
        this.isSuccess = isSuccess;
        this.data = data;
    }
}
