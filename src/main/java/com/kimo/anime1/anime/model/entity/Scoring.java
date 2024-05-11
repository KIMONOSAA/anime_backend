package com.kimo.anime1.anime.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * 评分
 * @author  kimo
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Scoring {

    /**
     * 评分id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 视频id关联
     */
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    /**
     * 用户id关联
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 评分
     */
    private double score;

    /**
     * 信息
     */
    private String comment;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 在实体持久化之前调用，用于设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        this.createTime = new Date();
        this.updateTime = new Date(); // 也可以在这里设置updateTime为当前时间
    }

    /**
     * 在实体更新之前调用，用于设置更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        this.updateTime = new Date();
    }

}
