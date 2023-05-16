package com.ikikyou.practice.common.exception;

import java.io.Serial;

/**
 * @author ikikyou
 * @date 2023/03/21 10:09
 */
public class BusinessException extends RuntimeException{
    private Integer code;

    @Serial
    private static final long serialVersionUID = -1190063963933699844L;

    public BusinessException(){

    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
