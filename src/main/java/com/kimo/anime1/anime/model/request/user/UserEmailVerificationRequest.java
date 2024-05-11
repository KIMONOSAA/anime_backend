package com.kimo.anime1.anime.model.request.user;

/**
 * @author  kimo
 * @param userId
 * @param code
 */
public record UserEmailVerificationRequest(
        String userId,
        String code
) {
}
