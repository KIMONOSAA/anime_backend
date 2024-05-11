package com.kimo.anime1.anime.model.response;

import com.kimo.anime1.anime.model.entity.Video;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class VideoUrlResponse {
    
     private Long id;

    private String url;

    private Video video;
}
