package com.kimo.anime1.anime.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 评论页面类
 * @author <a href="https://github.com/KIMONOSAA“ /> kimo
 */
@Builder
@Data
public class CommentsPageResponse {

    private String parentName;

    private String context;

    private Long commentId;

    private UUID userId;

    private int SonCount;

    private String username;

    private int likeCount;

    private byte[] avatar;

    private LocalDateTime createdAt;

}
