package com.kimo.anime1.anime.model.request.post;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class PostUpdateRequest {

    /**
     * id
     */
    private long id;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 标题
     */
    private String title;

    /**
     * 性别（0-男, 1-女）
     */
    private Integer gender;


    /**
     * 地点
     */
    private String place;

    /**
     * 职业
     */
    private String job;

    /**
     * 联系方式
     */
    private String contact;



    /**
     * 内容（个人介绍）
     */
    private String content;

    /**
     * 照片地址
     */
    private String photo;

    /**
     * 状态（0-待审核, 1-通过, 2-拒绝）
     */
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    private String reviewMessage;

}