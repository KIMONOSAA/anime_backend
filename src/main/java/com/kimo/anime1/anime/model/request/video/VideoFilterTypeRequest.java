package com.kimo.anime1.anime.model.request.video;

import com.kimo.anime1.anime.comment.PageRequest;
import lombok.Data;

/**
 * 动漫过滤器
 * @author <a href="https://github.com/KIMONOSAA“ /> kimo
 */
@Data
public class VideoFilterTypeRequest extends PageRequest {
    private String channel;
    private String type;
    private String year;
}
