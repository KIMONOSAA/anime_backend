package com.kimo.anime1.anime;


import com.kimo.anime1.anime.model.entity.VideoUrl;
import com.kimo.anime1.anime.service.IVideoService;
import com.kimo.anime1.anime.service.IVideoUrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@SpringBootTest
public class VideoTest {


    @Autowired
    private IVideoUrlService videoUrlService;

    @Autowired
    private IVideoService videoService;

    @Test
    public void getVideoUrl(){
        UUID uuid = UUID.fromString("7c52cda3-547c-4661-ba03-d92442173c23");
        List<VideoUrl> videoUrl = videoService.findById(uuid).get().getVideoUrl();
        System.out.println(videoUrl.stream().map(VideoUrl::getId).toList());

    }

//    @Test
//    public void getAllVideoUrl(){
//        UUID uuid = UUID.fromString("7c52cda3-547c-4661-ba03-d92442173c23");
//        Long videoId = 1L;
//        String userEmail = "3248034755@qq.com";
//        VideoUrl VideoUrl = videoUrlService.findByIdAndVideo(uuid, videoId,userEmail);
//        System.out.println(VideoUrl.getUrl());
//    }

}
