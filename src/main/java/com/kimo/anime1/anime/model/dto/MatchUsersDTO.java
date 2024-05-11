package com.kimo.anime1.anime.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * 匹配用户DTO
 *
 * @Author  kimo
 */
@Data
public class MatchUsersDTO {
    private Long num;  
    private UUID videoId;
}