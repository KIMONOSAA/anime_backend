package com.kimo.anime1.anime.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class VideoInfoScoringResponse {

    private UUID id;
    private String alias;
    private String name;
    private Integer numberOfSections;
    private byte[] photo;
    private Double totalScore;
}
