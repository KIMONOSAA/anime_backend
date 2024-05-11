package com.kimo.anime1.anime.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * @author  kimo
 */
@Data
public class VideoDTO {
    private UUID id;

    private String type;

    public VideoDTO(UUID id, String type) {
        this.id = id;
        this.type = type;
    }
}
