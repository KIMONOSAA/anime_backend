package com.kimo.anime1.anime.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthentianResponse {
    
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refersh_token")
    private String refershToken;
}
