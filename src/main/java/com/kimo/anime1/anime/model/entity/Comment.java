package com.kimo.anime1.anime.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 评论
 * @author  kimo
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    /**
     * 评论id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    /**
     * 评论内容
     */
    private String context;

    /**
     * 关联用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 关联视频url
     */
    @ManyToOne
    @JoinColumn(name = "url_id")
    private VideoUrl videoUrl;

    /**
     * 关联视频数据信息
     */
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    /**
     * 父评论
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    /**
     * 子评论
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> replies;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

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
     * 点赞数量
     */
    private Integer likeCount;

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
    public void addReply(Comment reply) {
        if (this.replies == null) {
            this.replies = new ArrayList<>();
        }
        reply.setParent(this);
        this.replies.add(reply);
    }


}
