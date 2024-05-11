package com.kimo.anime1.anime.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

import com.kimo.anime1.anime.comment.BaseResponse;
import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.comment.UseIdHolder;
import com.kimo.anime1.anime.enums.ResultsUtils;
import com.kimo.anime1.anime.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.model.entity.UserFile;
import com.kimo.anime1.anime.model.entity.UserInfo;
import com.kimo.anime1.anime.model.request.info.UserInfoRequest;
import com.kimo.anime1.anime.model.response.UserFIleResponse;
import com.kimo.anime1.anime.model.response.UserInfoAvatarResponse;
import com.kimo.anime1.anime.model.response.UserInfoResponse;
import com.kimo.anime1.anime.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户信息控制器
 * @param <>
 * @author kimo
 */
@Slf4j
@RestController
@RequestMapping("/userinfos")
@RequiredArgsConstructor
public class UserInfoController {


    private final IUserService userService;

    /**
     * 获取用户信息
     * @param newUserInfo
     * @return
     */
    @PostMapping("/info")
    public BaseResponse<UserInfoResponse> CreateUserInfoAndAvatar(@RequestBody UserInfoRequest newUserInfo){
        User userForToken = UseIdHolder.getUserForToken();
        UserInfo userInfo = userForToken.getUserInfo();
        if(userInfo != null){
            Optional<UserInfoResponse> userInfoResponse = userService.updatedUserInfo(userInfo,newUserInfo);
            return userInfoResponse.map(ResultsUtils::success).orElseGet(() -> ResultsUtils.error(ErrorCode.NULL_ERROR, "更新失败"));
        }else{
            Optional<UserInfoResponse> userInfoResponse = userService.createdUserInfo(newUserInfo,userForToken);
            return userInfoResponse.map(ResultsUtils::success).orElseGet(() -> ResultsUtils.error(ErrorCode.PARAMS_ERROR, "请检查你的格式"));
        }
    }

    /**
     * 上传头像
     * @param file

     * @throws IOException
     */
    @PostMapping("/file")
    @Transactional
    public BaseResponse<UserFIleResponse> uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        User userForToken = UseIdHolder.getUserForToken();
        UserFIleResponse userFile = userService.getUserByFile(file,userForToken);
        return ResultsUtils.success(userFile);

    }


    /**
     * 获取用户信息和头像
     * @param request
     * @return
     */
    @GetMapping("/userInfoAndAvatar")
    @Transactional
    public BaseResponse<UserInfoAvatarResponse> getUserInfo(HttpServletRequest request){
                User userForToken = UseIdHolder.getUserForToken();
                UserInfo userInfo = userForToken.getUserInfo();
                if(userInfo == null){
                    throw new BusinessException(ErrorCode.NULL_ERROR,"没有此用户的信息");
                }
                UserFile userFile = userForToken.getUserFIle();
                Blob avatarBlob = null;
                if(userFile != null){
                    avatarBlob = userFile.getAvatar();
                }

                byte[] avatarBytes = null;

                    if(avatarBlob != null){
                        try {
                            avatarBytes = avatarBlob.getBytes(1, (int) avatarBlob.length());

                        } catch (SQLException e) {
                            throw new BusinessException(ErrorCode.FILE_ERROR,"文件类型错误");
                        }

                    }
                UserInfoAvatarResponse userInfoAvatarResponse = UserInfoAvatarResponse.builder()
                                                                .date(userInfo.getDate())
                                                                .description(userInfo.getDescription())
                                                                .file(avatarBytes)
                                                                .name(userInfo.getName())
                                                                .sex(userInfo.getSex())
                                                                .build();
                return ResultsUtils.success(userInfoAvatarResponse);
    }

}
