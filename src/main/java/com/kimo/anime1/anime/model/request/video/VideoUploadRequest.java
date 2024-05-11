package com.kimo.anime1.anime.model.request.video;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class VideoUploadRequest {
    private UUID videoId;
    private Integer section;
    private MultipartFile uploadFile;
}
