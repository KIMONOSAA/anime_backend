package com.kimo.anime1.anime.model.request.video;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VideoInfoAddRequest {
    private String videoName;
    private String videoAlias;
    private String videoType;
    private String videoDescription;
    private int videoNumberOfSections;
    private String videoAnimeCompany;
    private String videoDirectorName;
    private String videoComicBookAuthor;
    private String videoVoiceActor;
    private String date;
    private String animeType;
    private MultipartFile photoFile;
}
