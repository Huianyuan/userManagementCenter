package com.example.umcenterbacked.common;


/**
 * @author :lhy
 * @description :返回工具类
 * @date :2023/03/01 下午 10:27
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data 泛型参数
     * @return com.example.umcenterbacked.common.BaseResponse<T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "success");
    }


    public static BaseResponse error(int errorCode, String message, String description) {
        return new BaseResponse<>(errorCode, null,message, description);
    }

    /**
     * 失败
     *
     * @param errorCode 枚举类的错误码
     * @return com.example.umcenterbacked.common.BaseResponse
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
        // return new BaseResponse<>(errorCode.getCode(),null,errorCode.getMessage(), errorCode.getDescription());
    }


    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null,message, description);
    }

    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(),null, errorCode.getMessage(), description);
    }

}
