package com.kimo.anime1.anime.service.impl;

import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.config.AesProperties;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.dto.AccessTokenDTO;
import com.kimo.anime1.anime.model.entity.AccessTokenInfo;
import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.model.entity.VideoUrl;
import com.kimo.anime1.anime.model.request.video.AccessTokenVideoUrlRequest;
import com.kimo.anime1.anime.repository.UserRepository;
import com.kimo.anime1.anime.repository.VideoUrlRepository;
import com.kimo.anime1.anime.security.jwt.JwtService;
import com.kimo.anime1.anime.service.IVideoUrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.kimo.anime1.anime.contant.RedisContants.STATS;

/**
 * 视频url服务实现
 * @author  kimo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VideoUrlServerImpl implements IVideoUrlService {

    @Autowired
    private final VideoUrlRepository urlRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final JwtService jwtService;



    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AesProperties aesProperties;

    private final Map<String, AccessTokenInfo> tokenMap = new ConcurrentHashMap<>();

    @Override
    public AccessTokenDTO findByIdAndVideo(UUID uuid, Long videoId, HttpServletRequest request) throws IOException {


        final String header = request.getHeader("Authorization");
        if(!(header == null) && !header.startsWith("Bearer ")){
            final String jwt;
            final String userEmail;
            jwt = header.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            Optional<User> user = userRepository.findByEmail(userEmail);
            String key = STATS + uuid;
            if(user.isPresent()){
                stringRedisTemplate.opsForHyperLogLog().add(key, user.get().getId().toString());
                long oneWeekMillis = TimeUnit.DAYS.toMillis(7);
                boolean isSuccess = stringRedisTemplate.expire(key, oneWeekMillis, TimeUnit.MILLISECONDS);
                if(!isSuccess){
                    throw new BusinessException(ErrorCode.REDIS_EXPIRE_ERROR,"设置过期时间失败");
                }
            }
        }
        VideoUrl videoUrl = urlRepository.findByIdAndVideo(uuid, videoId);

        String[] encryptObject = aesProperties.encryptObject(videoUrl.getUrl());
        String accessToken = generateAccessTokenInfo(uuid, 300,false);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setEncryptedArrayString(encryptObject);
        accessTokenDTO.setToken(accessToken);
        return accessTokenDTO;
    }

    @Override
    public Object getUrlForToken(AccessTokenVideoUrlRequest accessTokenVideoUrlRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, ClassNotFoundException {

        if(isTokenValidAndUsedOnce(accessTokenVideoUrlRequest.getToken())){
            String encryptedString = accessTokenVideoUrlRequest.getEncryptedString();

            String encrypted = URLDecoder.decode(encryptedString, StandardCharsets.UTF_8);
            String encryptedIvString = accessTokenVideoUrlRequest.getEncryptedIvString();

            String encryptedIv = URLDecoder.decode(encryptedIvString, StandardCharsets.UTF_8);

            return aesProperties.decryptObject(encrypted, encryptedIv);
        }
        return null;
    }


    private String generateAccessTokenInfo(UUID videoId, int expireTime,boolean isEnbaled){
        UUID accessToken = UUID.randomUUID();
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo(accessToken.toString(), LocalDateTime.now().plusSeconds(expireTime), isEnbaled);

        tokenMap.put(accessToken.toString(), accessTokenInfo);
        return accessToken.toString();
    }

    public Boolean isTokenValidAndUsedOnce(String token){

        AccessTokenInfo accessTokenInfo = tokenMap.get(token);
        if(accessTokenInfo == null){
            return false;
        }
        if(accessTokenInfo.isExpired() || accessTokenInfo.getIsEnbaled()){
            tokenMap.remove(token);
            return false;
        }
        accessTokenInfo.setIsEnbaled(false);
        tokenMap.put(token, accessTokenInfo);
        return true;
    }

}
