package com.kimo.anime1.anime.redisCodeUtil;

import com.kimo.anime1.anime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Redis验证码工具类
 * @author  kimo
 */
@Service
public class RedisVerificationUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserRepository userRepository;

    public String generateVerification(){
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }

    public boolean verifyCode(String userId,String code,String key){
        key = key + userId;
        String storedCode = redisTemplate.opsForValue().get(key);
        return code.equals(storedCode);
    }

    public void deleteCode(String userId,String key){
        key = key + userId;
        redisTemplate.delete(key);
    }

}
