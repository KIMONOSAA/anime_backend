package com.kimo.anime1.anime.exception;

import com.kimo.anime1.anime.comment.BaseResponse;
import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.enums.ResultsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *@author  kimo
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultsUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultsUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }
}
