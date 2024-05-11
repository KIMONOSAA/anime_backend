package com.kimo.anime1.anime.service.impl;

import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.comment.UseIdHolder;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.entity.*;
import com.kimo.anime1.anime.model.request.comment.CommentsAllRequest;
import com.kimo.anime1.anime.model.request.comment.CommentsAllSonRequest;
import com.kimo.anime1.anime.repository.VideoCommentServerRepository;
import com.kimo.anime1.anime.repository.VideoRepository;
import com.kimo.anime1.anime.repository.VideoUrlRepository;
import com.kimo.anime1.anime.model.request.comment.CommentsAddRequest;
import com.kimo.anime1.anime.model.response.CommentsPageResponse;
import com.kimo.anime1.anime.service.IVideoCommentServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.kimo.anime1.anime.contant.RedisContants.COMMENT_LIKED_KEY;

/**
 * 视频评论服务实现
 * @Author  kimo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VideoCommentServerImpl implements IVideoCommentServer {


    private final VideoCommentServerRepository commentRepository;

    private final VideoRepository videoRepository;

    private final VideoUrlRepository videoUrlRepository;

    private final StringRedisTemplate stringRedisTemplate;

    public UUID getUserId() {
        // 从TokenHolder获取token
        return UseIdHolder.getUserForToken().getId();
    }

    public User getUser() {
        // 从TokenHolder获取token
        return UseIdHolder.getUserForToken();
    }
    /**
     * 添加评论
     * @param commentsAddRequest
     * @return
     */
    @Override
    @Transactional
    public String addComments(CommentsAddRequest commentsAddRequest) {
        UUID userId = this.getUserId();
        idVaildComment(commentsAddRequest,userId);
        Video video = videoRepository.findById(commentsAddRequest.getVideoUrlId()).orElseThrow(() -> new BusinessException(ErrorCode.VIDEO_ERROR,"没有这个视频"));
        User user = this.getUser();
        VideoUrl videoUrl = videoUrlRepository.findById(commentsAddRequest.getUrlId()).orElseThrow(() -> new BusinessException(ErrorCode.VIDEO_ERROR,"没有这个集数"));
        Comment comment;
        if(commentsAddRequest.getParent() != null){
            Optional<Comment> parent = commentRepository.findById(commentsAddRequest.getParent());
            comment = Comment.builder()
                    .video(video)
                    .videoUrl(videoUrl)
                    .context(commentsAddRequest.getContext())
                    .user(user)
                    .likeCount(0)
                    .isDelete(0)
                    .createdAt(LocalDateTime.now())
                    .build();
            parent.get().addReply(comment);
        }else{
            comment = Comment.builder()
                    .context(commentsAddRequest.getContext())
                    .user(user)
                    .videoUrl(videoUrl)
                    .isDelete(0)
                    .likeCount(0)
                    .video(video)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
        commentRepository.save(comment);
        return "评论成功";
    }

    /**
     * 更新评论
     * @param commentsAddRequest
     * @param id
     * @return
     */
    @Override
    public String updateComments(CommentsAddRequest commentsAddRequest, Long id) {
        UUID userId = this.getUserId();
        idVaildComment(commentsAddRequest,userId);
        //根据id找到对应的用户id与当前用户的id是否一致
        Comment comment1 = commentRepository.findByIdForUser(id, userId).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_ERROR, "没有这个评论"));
        comment1.setContext(commentsAddRequest.getContext());
        commentRepository.save(comment1);
        return "更新成功";

    }

    /**
     * 校验评论
     * @param commentsAddRequest
     */
    private static void idVaildComment(CommentsAddRequest commentsAddRequest, UUID userId) {
        if(commentsAddRequest.getContext() == null){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"评论不能为空");
        }
        if(commentsAddRequest.getVideoUrlId() == null){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"视频的id不能为空");
        }
        if(userId == null){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"用户的id不能为空");
        }
        if(commentsAddRequest.getUrlId() == null || commentsAddRequest.getUrlId() <= 0){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"当前集数的id不能为空");
        }
    }


    /**
     * 删除评论
     * @param id
     * @return
     */
    @Override
    public String deletedComments(Long id) {
            UUID userId = this.getUserId();
            if(id == null || id <= 0){
                throw new BusinessException(ErrorCode.COMMENT_ERROR,"id不能为空");
            }
            Comment comment = commentRepository.findByIdForUser(id, userId).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_ERROR, "没有这个评论"));
            comment.setIsDelete(1);
            commentRepository.save(comment);
            return "删除成功";

    }

    /**
     * 获取所有评论
     * @return
     */
    @Override
    public List<CommentsPageResponse> getAllProducts(CommentsAllRequest commentsAllRequest) {
        // 获取当前页、每页大小、排序字段、排序方式、内容
        long current = commentsAllRequest.getCurrent();
        long size = commentsAllRequest.getPageSize();
        String sortField = commentsAllRequest.getSortField();
        String sortOrder = commentsAllRequest.getSortOrder();
        UUID videoId = commentsAllRequest.getVideoId();
        Long urlId = commentsAllRequest.getUrlId();
        // 校验size以限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        // 创建 Sort 对象来实现动态排序
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);

        // 创建 PageRequest 对象来实现分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) size, sort);

        // 使用 JpaSpecificationExecutor 执行查询，返回分页结果
        Page<Comment> allComments = commentRepository.findByVideoAndParentIsNull(videoId,urlId, pageRequest);
        return getAllCommentsPage(allComments,this::getAllComments);
    }

    /**
     * 获取所有评论的子评论
     * @return
     */
    @Override
    public List<CommentsPageResponse> getAllProductsSon(CommentsAllSonRequest commentsAllSonRequest) {
        // 获取当前页、每页大小、排序字段、排序方式、内容
        long current = commentsAllSonRequest.getCurrent();
        long size = commentsAllSonRequest.getPageSize();
        String sortField = commentsAllSonRequest.getSortField();
        String sortOrder = commentsAllSonRequest.getSortOrder();
        UUID videoId = commentsAllSonRequest.getVideoId();
        Long urlId = commentsAllSonRequest.getUrlId();
        Long parentId = commentsAllSonRequest.getParentId();
        // 校验size以限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        // 创建 Sort 对象来实现动态排序
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);

        // 创建 PageRequest 对象来实现分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) size, sort);
        Page<Comment> allComments = commentRepository.findByVideoAndParent(videoId,urlId,parentId,pageRequest);
        return getAllCommentsPage(allComments,this::getAllComments);
    }

    /**
     * 巧妙的运用redis的ZSet集合来实现点赞功能
     * @param id
     * @return
     */
    @Override
    public String likeComment(Long id) {
        //1.获取登录用户
        String userId = this.getUserId().toString();
        //2.判断当前用户是否已经点赞
        String key = COMMENT_LIKED_KEY + id;
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        if(score == null) {
            //3.如果未点赞，可以点赞
            //3.1.数据库点赞＋1
            boolean isSuccess = commentRepository.incrementLikedCountById(id);
            //3.2保存用户到redis得set集合
            if(isSuccess){
                stringRedisTemplate.opsForZSet().add(key,userId,System.currentTimeMillis());
            }else{
                throw new BusinessException(ErrorCode.COMMENT_ERROR,"点赞失败");
            }
        }else {

            //4.如果已经点赞，取消点赞
            //3.1.数据库点赞-1
            boolean isSuccess = commentRepository.decreaseLikedCountById(id);
            //3.2把用户到redis的set集合移除
            if(isSuccess){
                stringRedisTemplate.opsForZSet().remove(key,userId.toString());
            }else{
                throw new BusinessException(ErrorCode.COMMENT_ERROR,"点赞失败");
            }

        }
        return "点赞成功";
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
     * 获取所有评论方法的方法
     * @param comment
     * @return
     */
    public CommentsPageResponse getAllComments(Comment comment){
        byte[] avatarByte = null;
        Blob avatarBlob = null;
        try {
            if(comment.getUser().getUserFIle().getAvatar() != null){
                avatarBlob = comment.getUser().getUserFIle().getAvatar();
                avatarByte = avatarBlob.getBytes(1,(int) avatarBlob.length());
            }
        }catch (SQLException e){
            throw new BusinessException(ErrorCode.COMMENT_ERROR,"你的照片有误");
        }
        return CommentsPageResponse.builder()
                .commentId(comment.getCommentId())
                .avatar(avatarByte)
                .SonCount(comment.getReplies().size())
                .likeCount(comment.getLikeCount())
                .context(comment.getContext())
                .parentName(comment.getParent() == null ? null : comment.getParent().getUser().getUserInfo().getName())
                .username(comment.getUser().getUserInfo().getName())
                .createdAt(comment.getCreatedAt())
                .userId(comment.getUser().getId())
                .build();
    }


}
