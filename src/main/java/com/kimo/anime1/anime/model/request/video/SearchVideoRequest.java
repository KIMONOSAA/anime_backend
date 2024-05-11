package com.kimo.anime1.anime.model.request.video;

import com.kimo.anime1.anime.comment.PageRequest;
import lombok.Data;

/**
 * 搜索视频DTO
 *
 * @Author  kimo
 */
@Data
public class SearchVideoRequest extends PageRequest {

    private String search;  

}