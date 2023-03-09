package com.example.umcenterbacked.exception;

import com.example.umcenterbacked.common.BaseResponse;
import com.example.umcenterbacked.common.ErrorCode;
import com.example.umcenterbacked.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author :lhy
 * @description : 全局异常处理器
 * @date :2023/03/05 下午 01:25
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("businessException："+e.getDescription(),e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());

    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e){
        log.info("runtimeException",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR,e.getMessage(),"");

    }
}
