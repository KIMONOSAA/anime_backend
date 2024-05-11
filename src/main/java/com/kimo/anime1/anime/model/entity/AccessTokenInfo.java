package com.kimo.anime1.anime.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * access一次性过期令牌
 * @author  kimo
 */
@Data
public class AccessTokenInfo {

    private Boolean isEnbaled;
    private String token;
    private LocalDateTime expiredTime;

    public AccessTokenInfo(String token, LocalDateTime expiredTime,Boolean isEnbaled) {
        this.token = token;
        this.isEnbaled = isEnbaled;
        this.expiredTime = expiredTime;
    }

    public AccessTokenInfo() {

    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredTime);
    }





}
