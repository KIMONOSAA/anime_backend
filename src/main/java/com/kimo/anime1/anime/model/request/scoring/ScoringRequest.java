package com.kimo.anime1.anime.model.request.scoring;

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
public class ScoringRequest {


    /**
     * 视频id
     */
    private UUID videoId;


    /**
     * 评分
     */
    private Double score;

    /**
     * 信息
     */
    private String comment;

}
