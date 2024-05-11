package com.kimo.anime1.anime.comment;

import com.kimo.anime1.anime.model.entity.User;


/**
 * 用于存储用户信息
 * @author  kimo
 */
public class UseIdHolder {
    private static final ThreadLocal<User> tokenThreadLocal = new ThreadLocal<>();
  
    public static void setUserForToken(User token) {
        tokenThreadLocal.set(token);  
    }  
  
    public static User getUserForToken() {
        return tokenThreadLocal.get();  
    }  
  
    public static void clearUserId() {
        tokenThreadLocal.remove();  
    }  
}