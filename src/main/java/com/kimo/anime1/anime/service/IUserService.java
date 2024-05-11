package com.kimo.anime1.anime.service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.model.entity.UserInfo;
import com.kimo.anime1.anime.model.request.user.UserAuthenticationRequest;
import com.kimo.anime1.anime.model.request.info.UserInfoRequest;
import com.kimo.anime1.anime.model.request.user.UserAddRequest;
import com.kimo.anime1.anime.model.response.AuthentianResponse;
import com.kimo.anime1.anime.model.response.UserFIleResponse;
import com.kimo.anime1.anime.model.response.UserInfoResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 用户服务
 * @Author  kimo
 */
public interface IUserService {
    Optional<UserInfoResponse> createdUserInfo(UserInfoRequest newUserInfo,User user);
    User registration(UserAddRequest userResponse);
    Optional<AuthentianResponse> authentication(UserAuthenticationRequest request);
    void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException;
    boolean isTokenValid(String token, User user);
    Optional<UserInfoResponse> updatedUserInfo(UserInfo userInfo, UserInfoRequest newUserInfo);
    UserFIleResponse getUserByFile(MultipartFile file,User user) throws IOException;
    Map<String, Object> tokenValid(String token);

    String saveUserForUpdateEnabled(String userId,boolean isValid);

    Boolean findById(UUID id);

    String userLogOut(HttpServletRequest request);

//    boolean resetVerificationEmail(RestUserEmailVerification userEmail);
}


