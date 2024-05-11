package com.kimo.anime1.anime.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 视频地址
 * @author  kimo
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoUrl {

    /**
     * 视频地址id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 当前url地址的集数
     */
    private Integer section;

    /**
     * 视频地址对应的评论
     */
    @OneToMany(mappedBy = "videoUrl")
    private List<Comment> comment;

    /**
     * 视频地址对应的视频
     */
    @ManyToOne
    @JoinColumn(name = "video_video_id")
    private Video video;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Temporal(TemporalType.DATE)
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
