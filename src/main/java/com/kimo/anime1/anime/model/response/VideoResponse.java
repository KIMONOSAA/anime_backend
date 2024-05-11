package com.kimo.anime1.anime.model.response;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;

import lombok.Builder;
import lombok.Data;


@Data
// @Builder
public class VideoResponse {
    
    private UUID id;

    private String name;

    private String alias;

    private List<String> type;

    private String description;

    private int numberOfSections;

    private String photo;

    private String date;

    private String videoData;

    private String animeCompany;

    private String directorName;

    private List<String> voiceActor;

    private String comicBookAuthor;

    public VideoResponse(UUID id,String name,String alias,List<String> type,String description,int numberOfSections,byte[] photoBytes,String directorName,List<String> voiceActor,String date,byte[] videoBytes,String animeCompany,String comicBookAuthor){
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.description = description;
        this.numberOfSections = numberOfSections;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        this.videoData = videoBytes != null ? Base64.encodeBase64String(videoBytes) : null;
        this.date = date;
        this.directorName = directorName;
        this.voiceActor = voiceActor;
        this.animeCompany= animeCompany;
        this.comicBookAuthor = comicBookAuthor;
        this.type = type;
    }
}
