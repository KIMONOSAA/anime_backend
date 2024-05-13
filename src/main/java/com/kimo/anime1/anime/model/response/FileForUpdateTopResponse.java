package com.kimo.anime1.anime.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileForUpdateTopResponse {
    private byte[] file;
}
