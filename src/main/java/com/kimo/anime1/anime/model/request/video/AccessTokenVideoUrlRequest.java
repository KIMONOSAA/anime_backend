package com.kimo.anime1.anime.model.request.video;

import lombok.Data;

import java.util.UUID;

@Data
public class AccessTokenVideoUrlRequest {
    private String token;
    private UUID videoId;
    private Long section;
    private String encryptedString;
    private String encryptedIvString;

}
