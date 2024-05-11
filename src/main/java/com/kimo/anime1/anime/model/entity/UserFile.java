package com.kimo.anime1.anime.model.entity;

import java.sql.Blob;
import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户文件
 * @author  kimo
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFile {

    /**
     * 用户文件id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    /**
     * 文件头像
     */
    @Lob
    private Blob avatar;


    /**
     * 对应的用户名
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


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
