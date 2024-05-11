package com.kimo.anime1.anime.enums;


import com.kimo.anime1.anime.comment.BaseResponse;
import com.kimo.anime1.anime.comment.ErrorCode;
import lombok.Data;

/**
 * 状态码
 * @author  kimo
 */
@Data
public class ResultsUtils {


    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }


    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }



}
