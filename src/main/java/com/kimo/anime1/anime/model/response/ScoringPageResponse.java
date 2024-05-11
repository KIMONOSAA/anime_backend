package com.kimo.anime1.anime.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * 评论页面类
 * @author <a href="https://github.com/KIMONOSAA“ /> kimo
 */
@Builder
@Data
public class ScoringPageResponse {

    private String context;

    private UUID videoId;

    private UUID userId;


    private String username;

    private byte[] avatar;

    private Date createdAt;

}
