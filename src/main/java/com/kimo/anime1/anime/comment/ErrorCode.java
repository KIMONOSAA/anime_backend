package com.kimo.anime1.anime.comment;


/**
 * 错误码
 * @author  kimo
 */
public enum ErrorCode {
    SUCCESS(0, "成功", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    TOKEN_INVALID(400001,"Token失效",""),
    NO_TOKEN(40101,"Token错误",""),
    FORBIDDEN(40301, "禁止访问", ""),
    SYSTEM_ERROR(50000, "系统内部异常", ""),
    EMAIL_ERROR(40002, "电子邮件发送失败", ""),
    VIDEO_NOT_FOUND(40002, "视频不存在", ""),
    VIDEO_ERROR(40002, "视频解析失败", ""),
    COMMENT_ERROR(40003, "评论错误", ""),


    FILE_ERROR(40001, "文件类型错误", ""),
    REDIS_EXPIRE_ERROR(40005, "设置时间失效","" ), NOT_FOUND_ERROR(40001, "没有这个文章", "");

    private Integer code;

    private String description;

    private String message;

    public Integer getCode() {
        return code;
    }

    ErrorCode(Integer code, String description, String message) {
        this.code = code;
        this.description = description;
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

}
