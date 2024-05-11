package com.kimo.anime1.anime.model.request.comment;

import com.kimo.anime1.anime.comment.PageRequest;
import lombok.Data;

import java.util.UUID;


@Data
public class CommentsAllRequest extends PageRequest {

    private UUID videoId;
    private Long urlId;
}
