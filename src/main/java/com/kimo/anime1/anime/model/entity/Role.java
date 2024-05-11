package com.kimo.anime1.anime.model.entity;


import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.kimo.anime1.anime.model.entity.Permission.ADMIN_CREATE;
import static com.kimo.anime1.anime.model.entity.Permission.ADMIN_DELETE;
import static com.kimo.anime1.anime.model.entity.Permission.ADMIN_READ;
import static com.kimo.anime1.anime.model.entity.Permission.ADMIN_UPDATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 角色
 * @author  kimo
 */
@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
        Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE
        )
    );

    
    @Getter
    private final Set<Permission> permissions;


    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authention = getPermissions()
                                            .stream()
                                            .map(
                                                permissions -> new SimpleGrantedAuthority(permissions.getPermission())
                                            ).collect(Collectors.toList());
        authention.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authention;         
    }
}
