package com.kimo.anime1.anime.model.request.video;

import com.kimo.anime1.anime.comment.PageRequest;
import lombok.Data;

@Data
public class VideoAllYearsRequest extends PageRequest {
    private String year;
}
