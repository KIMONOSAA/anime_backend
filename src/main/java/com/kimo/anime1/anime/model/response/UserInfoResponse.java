package com.kimo.anime1.anime.model.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Long userId;    
    private String name;
    private String description;
    private String sex;
    private LocalDate date;
}
