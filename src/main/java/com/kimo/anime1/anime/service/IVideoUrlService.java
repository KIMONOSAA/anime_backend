package com.kimo.anime1.anime.service;

import com.kimo.anime1.anime.model.dto.AccessTokenDTO;
import com.kimo.anime1.anime.model.request.video.AccessTokenVideoUrlRequest;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 视频url服务
 * @Author  kimo
 */
public interface IVideoUrlService {

    /**
     * 根据视频id和urlId查询
     * @param uuid
     * @param videoId
     * @return
     */
    AccessTokenDTO findByIdAndVideo(UUID uuid, Long videoId, HttpServletRequest request) throws IOException;

    Object getUrlForToken(AccessTokenVideoUrlRequest accessTokenVideoUrlRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, ClassNotFoundException;
}
