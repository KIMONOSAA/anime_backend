package com.kimo.anime1.anime.model.request.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 评论请求类
 * @author  kimo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsAddRequest {
    private String context;

    private Long urlId;

    private UUID videoUrlId;

    private Long parent;

}
