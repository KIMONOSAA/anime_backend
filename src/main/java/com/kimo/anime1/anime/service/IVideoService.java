package com.kimo.anime1.anime.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kimo.anime1.anime.model.request.video.SearchVideoRequest;
import com.kimo.anime1.anime.model.request.video.VideoAllYearsRequest;
import com.kimo.anime1.anime.model.request.video.VideoFilterTypeRequest;
import com.kimo.anime1.anime.model.request.video.VideoUpdateRequest;
import com.kimo.anime1.anime.model.response.VideoInfoScoringResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.kimo.anime1.anime.model.entity.Video;

/**
 * 视频服务
 * @Author  kimo
 */
public interface IVideoService {

    void addVideo(String videoName, String videoAlias, List<String> videoType, String videoDescription, int videoNumber, String videoAnimeCompany, String videoDirectorName, String videoComicBookAuthor, List<String> videoVoiceActor,String date,String animeType, MultipartFile photoFile);

    Page<Video> getAllVideos(com.kimo.anime1.anime.comment.PageRequest pageRequest);

    Optional<Video> getVideo(UUID videoId);

	void deleteVideoById(UUID id);

    VideoUpdateRequest updatedVideoById(UUID id, VideoUpdateRequest video);

    Page<Video> findByData(VideoAllYearsRequest allYearsRequest);

    //已废弃
    // int getNumberOfSectionsById(UUID uuid);

    Optional<Video> findById(UUID uuid);


    List<VideoInfoScoringResponse> getAllvideoHotop();

    List<VideoInfoScoringResponse> getAllVideoScores();

    List<Video> matchUsers(long num, UUID videoId);

    Page<Video> findBySearchTerm(SearchVideoRequest searchVideoRequest);

    Page<Video> findByType(VideoFilterTypeRequest videoFilterTypeRequest);

    List<Video> listVideo();

    List<Video> listVideoForAnimeType(String animeType);

    List<Video> listVideoForAnimeDateInChase(Date animeDate);
}
