package com.example.umcenterbacked.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :lhy
 * @description : 通用返回类
 * @date :2023/03/01 下午 10:16
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 810681898723316324L;
    //状态码
    private int code;
    //数据
    private T data;
    //提示信息
    private String message;
    //详细描述
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data,String message) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = "";
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = "";
        this.description = "";
    }

    public BaseResponse(ErrorCode errorCode) {
        this.code =errorCode.getCode();
        this.data=null;
        this.message = errorCode.getMessage();
        this.description = errorCode.getDescription();
    }

}
