package com.kimo.anime1.anime.service;


import com.kimo.anime1.anime.model.entity.Post;

/**
 * 帖子服务
 * @Author  kimo
 */
public interface PostService {

    /**
     * 校验
     *
     * @param post
     * @param add 是否为创建校验
     */
    void validPost(Post post, boolean add);
}
