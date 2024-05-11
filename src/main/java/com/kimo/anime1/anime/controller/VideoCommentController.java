package com.kimo.anime1.anime.controller;


import com.kimo.anime1.anime.comment.BaseResponse;
import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.enums.ResultsUtils;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.request.comment.CommentsAddRequest;
import com.kimo.anime1.anime.model.request.comment.CommentsAllRequest;
import com.kimo.anime1.anime.model.request.comment.CommentsAllSonRequest;
import com.kimo.anime1.anime.model.response.CommentsPageResponse;
import com.kimo.anime1.anime.service.IVideoCommentServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 *
 * @author  kimo
 */
@Slf4j
@RestController
@RequestMapping("/comments")
public class VideoCommentController {


    @Autowired
    private IVideoCommentServer videoCommentServer;

    /**
     * 添加评论
     * @param commentsAddRequest
     * @return
     */
    @PostMapping("/addComments")
    public BaseResponse<String> addComments(@RequestBody CommentsAddRequest commentsAddRequest){

        if(commentsAddRequest.getContext() == null){
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR,"评论内容不能为空");
        }
        String string = videoCommentServer.addComments(commentsAddRequest);
        if("评论成功".equals(string)){
            return ResultsUtils.success(string);
        }
        return ResultsUtils.error(ErrorCode.COMMENT_ERROR);
    }

    /**
     * 更新评论
     * @param commentsAddRequest
     * @param id
     * @return
     */
    @PutMapping("/put-comments/{id}")
    public BaseResponse<String> updatedComments(@RequestBody CommentsAddRequest commentsAddRequest, @PathVariable("id") Long id){
        if(id == null){
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR,"id不能为空");
        }
        if(id <= 0){
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR,"id不能小于0");
        }
        String string = videoCommentServer.updateComments(commentsAddRequest, id);
        if("更新成功".equals(string)){
            return ResultsUtils.success(string);
        }else{
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR);
        }
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping("/deleted/{id}")
    public String deletedComments(@PathVariable("id") Long id){
        return videoCommentServer.deletedComments(id);
    }


    /**
     * 获取所有评论
     * @return
     */
    @PostMapping("/list/parent")
    @Transactional
    public BaseResponse<List<CommentsPageResponse>> getAllComments(@RequestBody CommentsAllRequest commentsAllRequest){
        if (commentsAllRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<CommentsPageResponse> allProducts = videoCommentServer.
                getAllProducts(commentsAllRequest);

        return ResultsUtils.success(allProducts);
    }

    /**
     * 获取所有子评论
     * @return
     */
    @PostMapping("/list/son")
    @Transactional
    public BaseResponse<List<CommentsPageResponse>> getAllCommentsSon(@RequestBody CommentsAllSonRequest commentsAllSonRequest){
        if (commentsAllSonRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<CommentsPageResponse> allProductsSon = videoCommentServer.
                getAllProductsSon(commentsAllSonRequest);
        return ResultsUtils.success(allProductsSon);
    }


    /**
     * 点赞
     * @param id
     * @return
     */
    @PutMapping("/like/{id}")
    public BaseResponse<String> like(@PathVariable("id") Long id){
        if(id == null){
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR,"id不能为空");
        }
        if(id <= 0){
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR,"id不能小于0");
        }
        String string = videoCommentServer.likeComment(id);
        if("点赞成功".equals(string)){
            return ResultsUtils.success(string);
        }else{
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR);
        }
    }


}
