package com.kimo.anime1.anime.model.request.user;

import com.kimo.anime1.anime.model.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户请求类
 *
 * @author  kimo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String confirmPassword;

    /**
     * 角色
     */
    private Role role;

    /**
     * 邮箱
     */
    private String email;
}
