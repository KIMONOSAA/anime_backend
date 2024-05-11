package com.kimo.anime1.anime.model.response;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoInfoResponse {
    private UUID id;
    private String alias;
    private String name;
    private String description;
    private byte[] photo;
    private List<String> type;
    private String date;
    private String animeCompany;
    private String directorName;
    private List<String> voiceActor;
    private String comicBookAuthor;
}
