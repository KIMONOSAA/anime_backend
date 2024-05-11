package com.kimo.anime1.anime.model.request.user;


import lombok.Data;

import java.util.UUID;

/**
 * 发布事件请求类
 * @author  kimo
 * */
@Data
public class UserPublishEventRequest {
    /**
     * 用户id
     */
    private UUID id;
}
