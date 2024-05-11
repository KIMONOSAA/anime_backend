package com.kimo.anime1.anime.repository;

import com.kimo.anime1.anime.model.entity.Comment;
import com.kimo.anime1.anime.model.entity.Scoring;
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
public interface VideoScoringServiceRepository extends JpaRepository<Scoring,Long> {


    @Query("SELECT c FROM Scoring c WHERE c.video.id = :videoId AND c.isDelete = 0 ORDER BY c.score ASC")
    Page<Scoring> findByVideo(UUID videoId, Pageable pageable);

    @Query("SELECT s FROM Scoring s WHERE s.video.id = :videoId AND s.user.id = :userId AND s.isDelete = 0")
    Optional<Scoring> findByVideoIdAndUserId(UUID videoId, UUID userId);

    @Query("SELECT AVG(s.score) FROM Scoring s WHERE s.video.id = :videoId AND s.isDelete = 0")
    double calculateAverageScoreByVideoId(UUID videoId);

    @Query("SELECT s FROM Scoring s WHERE s.id = :id AND s.user.id = :userId AND s.isDelete = 0")
    Optional<Scoring> findByIdForUser(Long id, UUID userId);
}
