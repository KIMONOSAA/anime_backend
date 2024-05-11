package com.kimo.anime1.anime.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * 权限枚举类
 * @author  kimo
 */
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    ;

    @Getter
    private final String permission;
}
