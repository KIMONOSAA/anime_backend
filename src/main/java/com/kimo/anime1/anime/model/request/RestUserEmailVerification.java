package com.kimo.anime1.anime.model.request;

/**
 * 用户邮箱验证请求类
 * @author  kimo
 * @param email
 * @param userId
 * @param code
 */
public record RestUserEmailVerification(
        String email,

        String userId,
        String code
) {

}
