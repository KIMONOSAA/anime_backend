package com.kimo.anime1.anime.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kimo.anime1.anime.model.entity.VideoUrl;
import org.springframework.data.jpa.repository.Query;

/**
 * 视频链接仓库
 * @author  kimo
**/
public interface VideoUrlRepository extends JpaRepository<VideoUrl,Long> {


    //todo 修改应该是根据videoid的集数而不是videoid对应的urlid
    @Query("SELECT c FROM VideoUrl c WHERE c.id = :videoId AND c.video.id = :uuid")
    VideoUrl findByIdAndVideo(UUID uuid, Long videoId);

    @Query("SELECT c FROM VideoUrl c WHERE c.createTime = :animeDate")
    List<VideoUrl> findByCreateTime(Date animeDate);
}
