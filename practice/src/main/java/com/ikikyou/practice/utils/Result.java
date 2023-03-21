package com.ikikyou.practice.utils;

import com.ikikyou.practice.constant.StatusCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一返回结果集
 * @author ikikyou
 */
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -9150650992545177273L;

    /**
     * 是否成功
     */
    private boolean flag;

    /**
     *  状态码
     */
    private Integer code;

    /**
     *描述
     */
    private String message;

    /**
     *返回对象
     */
    private T data;

    public Result(boolean flag, Integer code, String message, T data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data =  data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
        this.flag = true;
        this.code = StatusCode.OK;
        this.message = "操作成功!";
    }

    public static <T> Result<T> ok(){
        return restResult(null,StatusCode.OK,null,true);
    }

    public static <T> Result<T> ok(String msg){
        return restResult(null,StatusCode.OK,msg,true);
    }

    public static <T> Result<T> ok(T data){
        return restResult(data,StatusCode.OK,null,true);
    }

    public static <T> Result<T> ok(T data,String msg){
        return restResult(data,StatusCode.OK,msg,true);
    }

    public static <T> Result<T> fail(){
        return restResult(null,StatusCode.ERROR,null,false);
    }

    public static <T> Result<T> fail(T data){
        return restResult(data, StatusCode.ERROR,null,false);
    }

    public static <T> Result<T> fail(T data,String msg){
        return restResult(data,StatusCode.ERROR,msg,false);
    }

    public static <T> Result<T> fail(Integer code,String msg){
        return restResult(null,code,msg,false);
    }
    public static <T> Result<T> fail(T data,Integer code,String msg){
        return restResult(data,code,msg,false);
    }

    public static <T> Result<T> process(Boolean success, String msg) {
        return null != success && !Boolean.FALSE.equals(success) ? ok() : fail(msg);
    }
    public static <T> Result<T> fail(String msg){
        return restResult(null,StatusCode.ERROR,msg,false);
    }

    public static  <T> Result<T> restResult(T data,Integer code,String msg,Boolean flag){
        Result<T> res=new Result<>();
        res.setCode(code);
        res.setFlag(flag);
        res.setMessage(msg);
        res.setData(data);
        return res;
    }

    @Override
    public String toString() {
        return "Result(code=" + this.getCode() + ", msg=" + this.getMessage() + ", success=" + this.getFlag() + ", data=" + this.getData() + ")";
    }


    public Result(int code, String message, Boolean flag, T data) {
        this.code = code;
        this.message = message;
        this.flag = flag;
        this.data = data;
    }



    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public Result<T> setMsg(String msg) {
        this.message = msg;
        return this;
    }

    public Result<T> setSuccess(Boolean flag) {
        this.flag = flag;
        return this;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Boolean getFlag() {
        return this.flag;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
