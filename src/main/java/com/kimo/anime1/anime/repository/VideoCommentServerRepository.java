package com.kimo.anime1.anime.repository;

import com.kimo.anime1.anime.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

/**
 * 视频评论服务仓库
 * @author  kimo
 */
public interface VideoCommentServerRepository extends JpaRepository<Comment,Long> {


    /**
     * 根据视频和父评论查询评论
     * @param videoId
     * @param urlId
     * @param pageable
     * @return
     */
    @Query("SELECT c FROM Comment c WHERE c.video.id = :videoId AND c.videoUrl.id = :urlId AND c.parent IS NULL AND c.isDelete = 0")
    Page<Comment> findByVideoAndParentIsNull(UUID videoId,Long urlId, Pageable pageable);

    /**
     * 根据视频和父评论查询有父评论的子评论
     * @param videoId
     * @param urlId
     * @param parentId
     * @param pageRequest
     * @return
     */
    @Query("SELECT c FROM Comment c WHERE c.video.id = :videoId AND c.videoUrl.id = :urlId AND c.parent.commentId = :parentId AND c.isDelete = 0")
    Page<Comment> findByVideoAndParent(UUID videoId,Long urlId, Long parentId, PageRequest pageRequest);

    /**
     * 根据id和用户查询评论
     * @param id
     * @param userId
     * @return
     */
    @Query("SELECT c FROM Comment c WHERE c.commentId = :id AND c.user.id = :userId AND c.isDelete = 0")
    Optional<Comment> findByIdForUser(Long id, UUID userId);

    @Query("UPDATE Comment c SET c.likeCount = c.likeCount + 1 WHERE c.commentId = :id AND c.isDelete = 0")
    boolean incrementLikedCountById(Long id);

    @Query("UPDATE Comment c SET c.likeCount = c.likeCount - 1 WHERE c.commentId = :id AND c.isDelete = 0 AND c.likeCount > 0")
    boolean decreaseLikedCountById(Long id);
}
