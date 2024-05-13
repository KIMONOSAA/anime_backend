package com.kimo.anime1.anime.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SectionUrlIdResponse {

    private Long UrlId;

    private Integer SectionId;
}
