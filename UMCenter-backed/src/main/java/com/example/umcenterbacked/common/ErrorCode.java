package com.example.umcenterbacked.common;


/**
 * 全局错误码
 */
public enum ErrorCode {

    SYSTEM_ERROR(50000,"系统内部异常",""),
    NO_USER(40201,"用户不存在",""),
    DATABASE_ERROR(50000,"数据库错误",""),
    SUCCESS(20000, "ok", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROE(40001, "请求参数为空", ""),
    NOT_LOGIN(40100, "未登录",""),
    NO_AUTH(40101,"无权限","");

    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}