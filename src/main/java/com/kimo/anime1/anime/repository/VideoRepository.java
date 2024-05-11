package com.kimo.anime1.anime.repository;

import java.util.List;
import java.util.UUID;

import com.kimo.anime1.anime.model.dto.VideoDTO;
import com.kimo.anime1.anime.model.dto.VideoInfoScoringDTO;
import com.kimo.anime1.anime.model.response.VideoInfoScoringResponse;
import com.kimo.anime1.anime.model.response.VideoTitleResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kimo.anime1.anime.model.entity.Video;

/**
 * 视频仓库
 * @author  kimo
 */

public interface VideoRepository extends JpaRepository<Video,UUID>, JpaSpecificationExecutor<Video> {

    void getVideoById(UUID videoId);

    @Query(value = "SELECT * FROM video v WHERE EXTRACT(YEAR FROM v.\"video_date\") = :year", nativeQuery = true)
    Page<Video> findByDate(@Param("year") String year,PageRequest of);

    @Query("SELECT v.numberOfSections FROM Video v WHERE v.id = :uuid")
    int findByNumberOfSections(UUID uuid);


    @Query("SELECT v FROM Video v ORDER BY v.videouv ASC LIMIT 50")
    List<Video> findAllByVideoUV();



//    @Query(value = "SELECT v.video_id, v.\"video-alias\", v.\"video-name\", v.\"video-num_ber_of_sections\", v.\"video-photo\", COALESCE(AVG(s.score), 0) AS totalScore ,v.\"video-anime_company\",v.\"anime-type\",v.\"video-comic_book_author\",v.create_time,v.update_time,v.\"video-date\",v.\"video-description\",v.\"video-director_name\",v.hot_top,v.is_delete,v.total_score,v.\"video-type\",v.videouv,v.\"video-voice_actor\" " +
//            "FROM Video v " +
//            "LEFT JOIN Scoring s ON v.video_id = s.video_id " +
//            "GROUP BY v.video_id, v.\"video-alias\", v.\"video-name\", v.\"video-photo\", v.\"video-num_ber_of_sections\" " +
//            "ORDER BY totalScore DESC, v.video_id DESC " +
//            "LIMIT 10", nativeQuery = true)
//    List<Video> findTop50VideosByScore();
//    @Query(value = "SELECT COALESCE(AVG(s.score), 0) AS totalScore " +
//            "FROM Video v " +
//            "LEFT JOIN Scoring s ON v.video_id = s.video_id " +
//            "GROUP BY v.video_id " +
//            "ORDER BY totalScore DESC, v.video_id DESC " +
//            "LIMIT 10", nativeQuery = true)

//    @Query("SELECT new com.kimo.anime1.anime.model.dto.VideoInfoScoringDTO(COALESCE(AVG(s.score), 0) AS totalScore) " +
//            "FROM Video v " +
//            "LEFT JOIN Scoring s ON v.id = s.videoId " +
//            "WHERE v.animeType = :animeType " +
//            "GROUP BY v.id " +
//            "ORDER BY totalScore DESC, v.id DESC " +
//            "LIMIT 50")
    //根据类型查询动漫的的所有最高热度
    @Query("SELECT v FROM Video v WHERE v.animeType = :animeType ORDER BY v.hotTop DESC LIMIT 10")
    List<Video> findTop50VideosByScore(String animeType);



    @Query("SELECT new com.kimo.anime1.anime.model.dto.VideoDTO(v.id, v.type) FROM Video v WHERE v.type IS NOT NULL")
    List<VideoDTO> findByIdAndByType();

    @Query("SELECT v FROM Video v WHERE v.id IN :videoIdList ")
    List<Video> findByIdForVideoIdList(List<UUID> videoIdList);

    @Query("SELECT v FROM Video v WHERE " +
            "v.name ILIKE CONCAT('%', :searchTerm, '%') OR " +
            "v.alias ILIKE CONCAT('%', :searchTerm, '%') OR " +
            "v.description ILIKE CONCAT('%', :searchTerm, '%') OR " +
            "v.animeCompany ILIKE CONCAT('%', :searchTerm, '%') OR " +
            "v.directorName ILIKE CONCAT('%', :searchTerm, '%') OR " +
            "v.comicBookAuthor ILIKE CONCAT('%', :searchTerm, '%')")
    Page<Video> findBySearchTerm(String searchTerm,PageRequest of);

    @Query("SELECT v FROM Video v ORDER BY v.hotTop DESC LIMIT 10")
    List<Video> findAllByVideoHotop();

    @Query("SELECT v FROM Video v WHERE v.animeType = :animeType ORDER BY v.hotTop LIMIT 12")
    List<Video> findByTypeList(String animeType);


//    @Query("SELECT v FROM Video v ")
//    Page<Video> findAll(PageRequest of);
}
