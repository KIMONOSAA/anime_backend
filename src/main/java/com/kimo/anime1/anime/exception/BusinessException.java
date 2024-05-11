package com.kimo.anime1.anime.exception;

import com.kimo.anime1.anime.comment.ErrorCode;
import lombok.Data;
import lombok.Getter;

/**
 * 业务异常
 * @author  kimo
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
    public int getCode() {
        return code;
    }
}
