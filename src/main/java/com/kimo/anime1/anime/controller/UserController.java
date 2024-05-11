package com.kimo.anime1.anime.controller;
import java.util.Optional;
import java.util.UUID;

import com.kimo.anime1.anime.comment.BaseResponse;
import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.enums.ResultsUtils;
import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.model.request.user.UserAuthenticationRequest;
import com.kimo.anime1.anime.model.request.user.UserPublishEventRequest;
import com.kimo.anime1.anime.model.request.user.UserEmailVerificationRequest;
import com.kimo.anime1.anime.model.request.user.UserAddRequest;
import com.kimo.anime1.anime.redisCodeUtil.RedisVerificationUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kimo.anime1.anime.model.response.AuthentianResponse;
import com.kimo.anime1.anime.service.impl.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.kimo.anime1.anime.contant.RedisContants.KEY_UTIL;

/**
 * 用户控制器
 * @param <>
 * @author  kimo
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final RedisVerificationUtil redisVerificationUtil;

    /**
     * 注册
     * @param request
     * @return
     */
    @PostMapping("/register")
    @Transactional
    public BaseResponse<String> register(@RequestBody UserAddRequest request){
        User user = userService.registration(request);
        String userId = user.getId().toString();
        return ResultsUtils.success(userId);
    }

    /**
     * 登录
     * @param
     * @return
     */
    @PostMapping("/publishevent")
    public BaseResponse<Boolean> getPublishEvent(@RequestBody UserPublishEventRequest userPublishEventRequest,HttpServletRequest request){
        UUID id = userPublishEventRequest.getId();
        if(id == null){
            return ResultsUtils.success(false);
        }
       return ResultsUtils.success(userService.findById(id));
    }

    /**
     * 验证电子邮箱
     * @param
     * @return
     */
    @PostMapping("/verificationEmail")
    public BaseResponse<String> checkVerificationEmail(@RequestBody UserEmailVerificationRequest userEmail){
            boolean isValid = redisVerificationUtil.verifyCode(userEmail.userId(),userEmail.code(),KEY_UTIL);
            String isEnable = userService.saveUserForUpdateEnabled(userEmail.userId(),isValid);
            return isEnable.equals("true") ? ResultsUtils.success(isEnable) : ResultsUtils.error(ErrorCode.EMAIL_ERROR,"验证失败");
    }


    /**
     * 登录
     * @param request
     * @return
     */
    @PostMapping("/authentication")
    public BaseResponse<AuthentianResponse> getAuthentication(@RequestBody UserAuthenticationRequest request){
        Optional<AuthentianResponse> optionalResponse = userService.authentication(request);
        if(optionalResponse.isPresent()){
            return ResultsUtils.success(optionalResponse.get());
        }else{
            return ResultsUtils.error(ErrorCode.NOT_LOGIN,"验证失败");
        }
    }


    /**
     * 登出
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request){
       String isLogout = userService.userLogOut(request);
       return ResultsUtils.success(isLogout);
    }


    

}
