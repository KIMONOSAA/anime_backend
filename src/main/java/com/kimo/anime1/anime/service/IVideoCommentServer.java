package com.kimo.anime1.anime.service;

import com.kimo.anime1.anime.model.request.comment.CommentsAddRequest;
import com.kimo.anime1.anime.model.request.comment.CommentsAllRequest;
import com.kimo.anime1.anime.model.request.comment.CommentsAllSonRequest;
import com.kimo.anime1.anime.model.response.CommentsPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;
/**
 * 视频评论服务
 * @Author  kimo
 */
public interface IVideoCommentServer {

    String addComments(CommentsAddRequest commentsAddRequest);

    String updateComments(CommentsAddRequest commentsAddRequest, Long id);

    String deletedComments(Long id);


    List<CommentsPageResponse> getAllProducts(CommentsAllRequest commentsAllRequest);

    List<CommentsPageResponse> getAllProductsSon(CommentsAllSonRequest commentsAllSonRequest);

    String likeComment(Long id);
}
