package com.example.umcenterbacked.exception;

import com.example.umcenterbacked.common.ErrorCode;

/**
 * @author :lhy
 * @description :全局异常类
 * @date :2023/03/05 上午 11:09
 */
public class BusinessException extends RuntimeException {


    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }


    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
