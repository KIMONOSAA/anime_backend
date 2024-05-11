package com.kimo.anime1.anime.comment;

import lombok.Data;


/**
 * 全局异常处理
 * @param <T>
 * @author  kimo
 */
@Data
public class BaseResponse<T> {

    private Integer code;

    private String message;

    private T data;

    private String description;

    public BaseResponse(Integer code, String message, T data, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }

    public BaseResponse(Integer code, T data, String message ) {
        this(code, message, data,"");
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),errorCode.getMessage(),null,errorCode.getDescription());
    }


    public BaseResponse(ErrorCode errorCode,String description){
        this(errorCode.getCode(),errorCode.getMessage(),null,description);
    }




}
