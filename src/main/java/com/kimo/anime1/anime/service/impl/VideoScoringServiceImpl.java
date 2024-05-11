package com.kimo.anime1.anime.service.impl;

import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.comment.UseIdHolder;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.entity.*;
import com.kimo.anime1.anime.model.request.scoring.ScoringRequest;
import com.kimo.anime1.anime.model.response.ScoringPageResponse;
import com.kimo.anime1.anime.repository.*;
import com.kimo.anime1.anime.service.IVideoScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 注意这是视频的评分模块而不是评论模块
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class VideoScoringServiceImpl implements IVideoScoringService {


    private final VideoScoringServiceRepository videoScoringServiceRepository;

    private final VideoRepository videoRepository;

    private final UserRepository userRepository;


    public UUID getUserId() {
        // 从TokenHolder获取token
        return UseIdHolder.getUserForToken().getId();
    }

    /**
     * 添加视频的评分
     * @param
     * @return
     */
    @Override
    @Transactional
    public String addComments(ScoringRequest scoringRequest, User user) {
        idVaildScoring(scoringRequest);
        Video video = videoRepository.findById(scoringRequest.getVideoId()).orElseThrow(() -> new BusinessException(ErrorCode.VIDEO_ERROR,"没有这个视频"));
        if(user == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN,"没有这个用户");
        }
        Optional<Scoring> videoIdAndUserId = videoScoringServiceRepository.findByVideoIdAndUserId(video.getId(), user.getId());
        if(videoIdAndUserId.isPresent()){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"你已经评论过了");
        }
        Scoring scoring = Scoring.builder()
                .comment(scoringRequest.getComment())
                .score(scoringRequest.getScore())
                .video(video)
                .isDelete(0)
                .user(user)
                .build();
        videoScoringServiceRepository.save(scoring);
        return "评分成功";

    }

    /**
     * 更新视频的评分
     * @param scoringRequest
     * @param id
     * @return
     */
    @Override
    public String updateComments(ScoringRequest scoringRequest, Long id) {
        UUID userId = this.getUserId();
        idVaildScoring(scoringRequest);
        Scoring scoring = videoScoringServiceRepository.findByIdForUser(id, userId).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_ERROR, "没有这个评分"));
        scoring.setComment(scoringRequest.getComment());
        scoring.setScore(scoringRequest.getScore());
        videoScoringServiceRepository.save(scoring);
        return "更新成功";
    }

    /**
     * 校验评论
     * @param scoringRequest
     */
    private static void idVaildScoring(ScoringRequest scoringRequest) {
        if(scoringRequest.getComment() == null){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"评论不能为空");
        }
        if(scoringRequest.getVideoId() == null){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"视频的id不能为空");
        }
    }


    /**
     * 删除视频的评分
     * @param id
     * @return
     */
    @Override
    public String deletedComments(Long id) {
            UUID userId = this.getUserId();
            if(id == null || id <= 0){
                throw new BusinessException(ErrorCode.COMMENT_ERROR,"id不能为空");
            }
            Scoring scoring = videoScoringServiceRepository.findByIdForUser(id, userId).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_ERROR, "没有这个评分"));
            scoring.setIsDelete(1);
            videoScoringServiceRepository.save(scoring);
            return "删除成功";
    }

    /**
     * 获取所有评论
     * @param of
     * @param videoId
     * @return
     */
    @Override
    public List<ScoringPageResponse> getAllProducts(PageRequest of, UUID videoId) {

        Page<Scoring> allComments = videoScoringServiceRepository.findByVideo(videoId, of);
        if(allComments == null){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"没有评论");
        }
        return getAllCommentsPage(allComments,this::getAllComments);
    }

    /**
     * 获取所有评论方法
     * @param allComments
     * @param commentMapper
     * @param <T>
     * @param <S>
     * @return
     */
    private <T, S> List<T> getAllCommentsPage(Page<S> allComments, Function<S, T> commentMapper) {
        return allComments.getContent().stream()
                .map(commentMapper)
                .collect(Collectors.toList());
    }


    /**
     * 获取所有视频的评分
     * @param scoring
     * @return
     */
    public ScoringPageResponse getAllComments(Scoring scoring){
        byte[] avatarByte = null;
        Blob avatarBlob = null;
        try {
            if(scoring.getUser().getUserFIle().getAvatar() != null){
                avatarBlob = scoring.getUser().getUserFIle().getAvatar();
                avatarByte = avatarBlob.getBytes(1,(int) avatarBlob.length());
            }else{
                throw new BusinessException(ErrorCode.COMMENT_ERROR,"你的照片有误");
            }
        }catch (SQLException e){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"你的照片有误");
        }
        return ScoringPageResponse.builder()
                .videoId(scoring.getVideo().getId())
                .avatar(avatarByte)
                .username(scoring.getUser().getUserInfo().getName())
                .createdAt(scoring.getCreateTime())
                .userId(scoring.getUser().getId())
                .context(scoring.getComment())
                .build();
    }


}
