package com.kimo.anime1.anime.model.entity;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


/**
 * 视频
 * @author kimo
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Video {

    /**
     * 视频id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "video_id", columnDefinition = "uuid")
    private UUID id;

    /**
     * 视频别名
     */
    @Column(name = "video_alias")
    private String alias;
    /**
     * 视频名
     */
    @Column(name = "video_name")
    private String name;


    /**
     * 视频集数
     */
    @Column(name = "video_numBerOfSections")
    private int numberOfSections;

    /**
     * 视频图片
     */

    @Lob
    @Column(name = "video_photo")
    private Blob photo;


    /**
     * 视频的总评分
     */
    private Double totalScore;


    /**
     * 视频类型
     */
    @Column(name = "video_type")
    private String type;


    /**
     * 视频描述
     */
    @Column(name = "video_description")
    private String description;






    /**
     * 视频发布日期
     */
    @Column(name = "video_date")
    private String date;




    /**
     * 视频公司
     */
    @Column(name = "video_animeCompany")
    private String animeCompany;


    /**
     * 视频导演
     */
    @Column(name = "video_directorName")
    private String directorName;


    /**
     * 视频编剧 ?还是视频声优忘了
     */
    @Column(name = "video_voiceActor")
    private String voiceActor;


    /**
     * 视频动画制作
     */
    @Column(name = "video_comicBookAuthor")
    private String comicBookAuthor;


    /**
     * 视频url
     */
    @OneToMany(mappedBy = "video",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VideoUrl> videoUrl;

    /**
     * 视频关联评论
     */
    @OneToMany(mappedBy = "video")
    @JsonIgnore
    private List<Comment> comments;


    /**
     *  视频关联评分
     */
    @OneToMany(mappedBy = "video")
    private List<Scoring> scorings;




    /**
     * 动漫类型
     */
    @Column(name = "anime_type")
    private String animeType;

    /**
     * 动漫浏览器次数
     */
    @Column(name = "videouv")
    private Long videouv;

    /**
     * 动漫热度
     */
    private double hotTop;

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








    