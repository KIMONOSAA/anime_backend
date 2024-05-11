package com.kimo.anime1.anime.model.response;

import java.sql.Blob;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAvatarResponse {
    private String name;
    private String description;
    private String sex;
    private LocalDate date;
    private byte[] file;

}
