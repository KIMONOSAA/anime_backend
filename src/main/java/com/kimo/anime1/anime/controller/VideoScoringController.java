package com.kimo.anime1.anime.controller;


import com.kimo.anime1.anime.comment.BaseResponse;
import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.comment.UseIdHolder;
import com.kimo.anime1.anime.enums.ResultsUtils;
import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.model.request.scoring.ScoringRequest;
import com.kimo.anime1.anime.model.response.ScoringPageResponse;
import com.kimo.anime1.anime.security.jwt.JwtService;
import com.kimo.anime1.anime.service.IVideoScoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 评分控制器
 *
 * @author  kimo
 */
@Slf4j
@RestController
@RequestMapping("/scoring")
public class VideoScoringController {

    @Autowired
    JwtService jwtService;

    @Autowired
    private IVideoScoringService videoScoringService;

    /**
     * 添加评分
     * @param scoringRequest
     * @return
     */
    @PostMapping("/addScore")
    public BaseResponse<String> addSoring(@RequestBody ScoringRequest scoringRequest){
        User user = UseIdHolder.getUserForToken();
        if(scoringRequest.getComment() == null){
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR,"评论内容不能为空");
        }
        String string = videoScoringService.addComments(scoringRequest,user);
        if("评论成功".equals(string)){
            return ResultsUtils.success(string);
        }
        return ResultsUtils.error(ErrorCode.COMMENT_ERROR);
    }

    /**
     * 更新评论
     * @param scoringRequest
     * @param id
     * @return
     */
    @PutMapping("/put-comments/{id}")
    public BaseResponse<String> updatedSoring(@RequestBody ScoringRequest scoringRequest, @PathVariable("id") Long id){
        if(id == null){
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR,"id不能为空");
        }
        if(id <= 0){
            return ResultsUtils.error(ErrorCode.COMMENT_ERROR,"id不能小于0");
        }
        String string = videoScoringService.updateComments(scoringRequest, id);
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
    public String deletedSoring(@PathVariable("id") Long id){
        return videoScoringService.deletedComments(id);
    }


    /**
     * 获取所有评论
     * 这个好像不是获取所有评论，当初只是写了获取前10条评论吧
     * @param page
     * @param size
     * @param videoId
     * @return
     */
    @GetMapping("/all/comment/{videoed}")
    @Transactional
    public BaseResponse<List<ScoringPageResponse>> getAllSorings(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @PathVariable("videoed") UUID videoId){
        if(size > 10){
            return ResultsUtils.error(ErrorCode.PARAMS_ERROR,"参数错误");
        }
        List<ScoringPageResponse> allProducts = videoScoringService.
                getAllProducts(PageRequest.of(page, size), videoId);
        return ResultsUtils.success(allProducts);
    }


}
