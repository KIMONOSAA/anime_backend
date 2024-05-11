package com.kimo.anime1.anime.service.impl;

import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.rowset.serial.SerialBlob;

import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.event.RegistrationCompleteEvent;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.redisCodeUtil.RedisVerificationUtil;
import com.kimo.anime1.anime.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimo.anime1.anime.model.entity.Token;
import com.kimo.anime1.anime.model.entity.TokenType;
import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.model.entity.UserFile;
import com.kimo.anime1.anime.model.entity.UserInfo;
import com.kimo.anime1.anime.repository.TokenRepostiory;
import com.kimo.anime1.anime.repository.UserFileRepostiory;
import com.kimo.anime1.anime.repository.UserInfoRepository;
import com.kimo.anime1.anime.repository.UserRepository;
import com.kimo.anime1.anime.model.request.user.UserAuthenticationRequest;
import com.kimo.anime1.anime.model.request.info.UserInfoRequest;
import com.kimo.anime1.anime.model.request.user.UserAddRequest;
import com.kimo.anime1.anime.model.response.AuthentianResponse;
import com.kimo.anime1.anime.model.response.UserFIleResponse;
import com.kimo.anime1.anime.model.response.UserInfoResponse;
import com.kimo.anime1.anime.security.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * 用户服务实现
 * @Author  kimo
 */
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    
    @Autowired
    private final UserRepository userRepository;

    private final RedisVerificationUtil redisVerificationUtil;
    private final UserFileRepostiory userFileRepostiory;
    private final TokenRepostiory tokenRepostiory;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserInfoRepository userInfoRepository;
    private final ApplicationEventPublisher publisher;
    public User registration(UserAddRequest userResponse) {
        this.examine(userResponse);
        Optional<User> user = userRepository.findByEmail(userResponse.getEmail());
        boolean validEmail = this.isEmail(userResponse.getEmail());
        if(user.isPresent() && validEmail){
            User newUser = user.get();
            newUser.setUsername(userResponse.getUsername());
            newUser.setPassword(passwordEncoder.encode(userResponse.getPassword()));
            return userRepository.save(newUser);
        }

        if(validEmail){
            User newUser = new User();
            newUser.setEmail(userResponse.getEmail());
            newUser.setPassword(passwordEncoder.encode(userResponse.getPassword()));
            newUser.setUsername(userResponse.getUsername());
            newUser.setRole(userResponse.getRole());
            newUser.setEnabled(false);
            return userRepository.save(newUser);

        }else{
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"电子邮件格式错误");
        }

    }

    private void examine(UserAddRequest userResponse) {

        if (StringUtils.isAnyBlank(userResponse.getEmail(), userResponse.getUsername(), userResponse.getConfirmPassword(), userResponse.getPassword())) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"请检查你的邮件,用户名,确认的密码是否为空");
        }
        if (userResponse.getUsername().length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名长度不小于4");
        }
        if (userResponse.getPassword().length() < 8 || userResponse.getConfirmPassword().length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码或确认密码长度不小于8");
        }
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userResponse.getUsername());
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名不能有特殊符号");
        }
        // 密码和校验密码相同
        if (!userResponse.getPassword().equals(userResponse.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和确认密码不相等");
        }
    }

    public  boolean isEmail(String email) {
        if (email == null || email.isEmpty() || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }


    public Optional<AuthentianResponse> authentication(UserAuthenticationRequest request){
          User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BusinessException(ErrorCode.NOT_LOGIN,"没有这个用户"));
          if(user.isEnabled()){
              authenticationManager.authenticate(
                      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
              );
              String jwtToken = jwtService.generateToken(user);
              String refreshToken = jwtService.generateRefreshToken(user);
              revokeAllUserToken(user);
              saveUserToken(user, jwtToken);
              return Optional.of(AuthentianResponse.builder()
                      .accessToken(jwtToken)
                      .refershToken(refreshToken)
                      .build());
          }
          return Optional.empty();
    }

    public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException{
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.userRepository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserToken(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthentianResponse.builder()
                .accessToken(accessToken)
                .refershToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }


    public void revokeAllUserToken(User user) {
        var validUserTokens = tokenRepostiory.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
        token.setExpired(true);
        token.setRevoked(true);
        });
        tokenRepostiory.saveAll(validUserTokens);
    }



    public void saveUserToken(User saveUser, String jwtToken) {
        Token token = Token.builder()
                      .user(saveUser)
                      .token(jwtToken)
                      .type(TokenType.BEARER)
                      .expired(false)
                      .revoked(false)
                      .build();
        tokenRepostiory.save(token);

    }



    public boolean isTokenValid(String token, User user) {
        return jwtService.isTokenValid(token, user);
    }



    public Optional<UserInfoResponse> updatedUserInfo(UserInfo userInfo, UserInfoRequest newUserInfo) {

      if(newUserInfo.getDate() != null){
          userInfo.setDate(newUserInfo.getDate());
      }
      if(StringUtils.isNotBlank(newUserInfo.getDesc())){
          userInfo.setDescription(newUserInfo.getDesc());
      }
      if(StringUtils.isNotBlank(newUserInfo.getName())){
          userInfo.setName(newUserInfo.getName());
      }
      if(StringUtils.isNotBlank(newUserInfo.getSex())){
          userInfo.setSex(newUserInfo.getSex());
      }
      UserInfo userInfoSelf = userInfoRepository.save(userInfo);
      if(userInfoSelf == null){
          throw new BusinessException(ErrorCode.NULL_ERROR);
      }else{
          UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                  .date(userInfoSelf.getDate())
                  .description(userInfoSelf.getDescription())
                  .sex(userInfoSelf.getSex())
                  .name(userInfoSelf.getName())
                  .build();
          return Optional.of(userInfoResponse);
      }
                            
    }



    public Optional<UserInfoResponse> createdUserInfo(UserInfoRequest newUserInfo,User user) {
        try {
          UserInfo userInfo = UserInfo.builder()
                                      .date(newUserInfo.getDate())
                                      .description(newUserInfo.getDesc())
                                      .name(newUserInfo.getName())
                                      .sex(newUserInfo.getSex())
                                        .isDelete(0)
                                      .user(user)
                                      .build();
          userInfoRepository.save(userInfo);

          UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                                          .date(userInfo.getDate())
                                          .description(userInfo.getDescription())
                                          .sex(userInfo.getSex())
                                          .name(userInfo.getName())
                                          .userId(userInfo.getId())
                                          .build();
          return Optional.of(userInfoResponse);
        } catch (Exception e) {
           e.printStackTrace();
            return Optional.empty();
        }
    }



    public UserFIleResponse getUserByFile(MultipartFile file, User user) throws IOException {
            Blob avatarPhoyo = null;
              try {
                if(file != null && !file.isEmpty()){
                  byte[] bytes = file.getBytes();
                  avatarPhoyo = new SerialBlob(bytes);
                }
              } catch (Exception e) {
                throw new BusinessException(ErrorCode.FILE_ERROR,"文件类型错误");
              }

            UserFile updatedUserFile = user.getUserFIle();
            if(updatedUserFile != null){
                updatedUserFile.setAvatar(avatarPhoyo);

                userFileRepostiory.save(updatedUserFile);
                return UserFIleResponse.builder()
                                                .file(avatarPhoyo)
                                                .build();
            }else{
                    UserFile userFIle = UserFile.builder()
                                      .avatar(avatarPhoyo)
                                      .isDelete(0)
                                      .user(user)
                                      .build();
                    userFileRepostiory.save(userFIle);
                return UserFIleResponse.builder()
                                                .file(avatarPhoyo)
                                                .build();

            }

    }



    @Override
    public Map<String, Object> tokenValid(String token) {
        Token tokens = tokenRepostiory.findByToken(token).orElseThrow(() -> new BusinessException(ErrorCode.NO_TOKEN,"你的token无效或者过期，请检查"));
        User user = tokens.getUser();
        boolean authenticated = this.isTokenValid(token,user);
        Map<String, Object> result = new HashMap<>();
        result.put("isValid", authenticated);
        result.put("user", user);
        return result;
    }

    public String saveUserForUpdateEnabled(String s, boolean isValid) {
        if(isValid){
            UUID userId = UUID.fromString(s);
            User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setEnabled(true);
            userRepository.save(user);
            return "true";
        }else{
            return "false";
        }
    }

    @Override
    public Boolean findById(UUID id) {
            UUID userId = id;
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                publisher.publishEvent(new RegistrationCompleteEvent(user.get(), redisVerificationUtil.generateVerification()));
                return true;
            }
            return false;
    }

    @Override
    public String userLogOut(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        String jwt;
        String userEmail;
        if (header == null || !header.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.NO_TOKEN,"没有token");
        }
        jwt = header.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new BusinessException(ErrorCode.NOT_LOGIN,"没有这个用户"));
            revokeAllUserToken(user);
            SecurityContextHolder.clearContext();
            return "登出成功";
        }
        return "登出失败";
    }


}
