package com.kimo.anime1.anime.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.request.video.SearchVideoRequest;
import com.kimo.anime1.anime.model.dto.VideoDTO;
import com.kimo.anime1.anime.model.entity.VideoUrl;
import com.kimo.anime1.anime.model.request.video.VideoAllYearsRequest;
import com.kimo.anime1.anime.model.request.video.VideoFilterTypeRequest;
import com.kimo.anime1.anime.model.request.video.VideoUpdateRequest;
import com.kimo.anime1.anime.model.response.VideoInfoScoringResponse;
import com.kimo.anime1.anime.service.IVideoService;
import com.kimo.anime1.anime.utils.AlgorithmUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kimo.anime1.anime.model.entity.Video;
import com.kimo.anime1.anime.repository.VideoRepository;
import com.kimo.anime1.anime.repository.VideoUrlRepository;

import lombok.RequiredArgsConstructor;

/**
 * 视频服务实现
 * @author  kimo
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VideoService implements IVideoService {


    private final String ANIME_TYPE = "日漫剧场";

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    VideoUrlRepository videoUrlRepository;

    public VideoService(VideoRepository videoRepositorys) {
        this.videoRepository = videoRepositorys;
    }


    @Override
    public void deleteVideoById(UUID id) {
        videoRepository.deleteById(id);
    }


    @Override
    public Optional<Video> getVideo(UUID videoId) {
        return videoRepository.findById(videoId);
    }

    @Override
    public Page<Video> getAllVideos(com.kimo.anime1.anime.comment.PageRequest pageRequest) {
        // 获取当前页、每页大小、排序字段、排序方式、内容
        long current = pageRequest.getCurrent();
        long size = pageRequest.getPageSize();
        String sortField = pageRequest.getSortField();
        String sortOrder = pageRequest.getSortOrder();
        // 校验size以限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        // 创建 Sort 对象来实现动态排序
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);

        // 创建 PageRequest 对象来实现分页
        PageRequest pageRequest1 = PageRequest.of((int) current, (int) size, sort);
        return videoRepository.findAll(pageRequest1);
    }


    @Override
    public void addVideo(String videoName, String videoAlias,  List<String> videoType, String videoDescription, int videoNumber, String videoAnimeCompany, String videoDirectorName, String videoComicBookAuthor, List<String> videoVoiceActor,String date,String animeType,MultipartFile photoFile) {
    Blob photoBlob = null;
    Blob videoBlob = null;
    try {
        if (photoFile != null && !photoFile.isEmpty()) {
            byte[] photoBytes = photoFile.getBytes();
            photoBlob = new SerialBlob(photoBytes);
        }
    } catch (SQLException | IOException ex) {
        ex.printStackTrace();
    }
    Video video = Video.builder()
                    .name(videoName)
                    .alias(videoAlias)
                    .type(String.valueOf(videoType))
                    .description(videoDescription)
                    .numberOfSections(videoNumber)
                    .animeCompany(videoAnimeCompany)
                    .directorName(videoDirectorName)
                    .comicBookAuthor(videoComicBookAuthor)
                    .voiceActor(String.valueOf(videoVoiceActor))
                    .date(date)
                    .isDelete(0)
                    .totalScore(0.0)
                    .videouv(0L)
                    .animeType(animeType)
                    .photo(photoBlob)
                    .build();
    videoRepository.save(video);
}


    @Override
    public VideoUpdateRequest updatedVideoById(UUID id, VideoUpdateRequest video) {
            Video videoz = videoRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.VIDEO_ERROR,"没有这该视频"));
            Blob photoBlob = null;
            Blob videoBlob = null;
            try {
                String stringToConvertPhoto = video.getPhoto();
                byte[] photoBytes = stringToConvertPhoto.getBytes(StandardCharsets.UTF_8);
                photoBlob = new SerialBlob(photoBytes);
            
                String stringToConvertVideo = video.getPhoto();
                byte[] videoBytes = stringToConvertVideo.getBytes(StandardCharsets.UTF_8);
                videoBlob = new SerialBlob(videoBytes);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            videoz.builder()
                    .name(video.getName())
                    .alias(video.getAlias())
                    .description(video.getDescription())
                    .numberOfSections(video.getNumberOfSections())
                    .animeCompany(video.getAnimeCompany())
                    .directorName(video.getDirectorName())
                    .comicBookAuthor(video.getComicBookAuthor())
                    .voiceActor(String.valueOf(video.getVoiceActor()))
                    .date(video.getDate())
                    .photo(photoBlob)
                    .build();
            return video;
    }


    @Override
    public Page<Video> findByData(VideoAllYearsRequest allYearsRequest) {
        // 获取当前页、每页大小、排序字段、排序方式、内容
        long current = allYearsRequest.getCurrent();
        long size = allYearsRequest.getPageSize();
        String sortField = allYearsRequest.getSortField();
        String sortOrder = allYearsRequest.getSortOrder();
        String year = allYearsRequest.getYear();
        // 校验size以限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        // 创建 Sort 对象来实现动态排序
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);

        // 创建 PageRequest 对象来实现分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) size, sort);
        return videoRepository.findByDate(year,pageRequest);
    }


    @Override
    public Optional<Video> findById(UUID uuid) {
        if(uuid == null){
            return Optional.empty();
        }
        return videoRepository.findById(uuid);
    }


    @Override
    public List<VideoInfoScoringResponse> getAllvideoHotop() {
        List<Video> videoList = videoRepository.findAllByVideoHotop();
        ArrayList<VideoInfoScoringResponse> VideoInfoScoringList = new ArrayList<>();
        getVideoForVideoInfoScoringResponse(videoList, VideoInfoScoringList);
        return VideoInfoScoringList;
    }



    @Override
    public List<VideoInfoScoringResponse> getAllVideoScores() {
        List<Video> allByVideoScoring = videoRepository.findTop50VideosByScore(ANIME_TYPE);
        ArrayList<VideoInfoScoringResponse> VideoInfoScoringList = new ArrayList<>();
        getVideoForVideoInfoScoringResponse(allByVideoScoring, VideoInfoScoringList);
        return VideoInfoScoringList;
    }

    /**
     * 匹配视频
     *
     * @param num
     * @param videoId
     * @return
     */
    @Override
    public List<Video> matchUsers(long num, UUID videoId) {
        Optional<Video> selfVideo = videoRepository.findById(videoId);
        if(!selfVideo.isPresent()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"没有找到这个视频");
        }
        List<VideoDTO> videoList = videoRepository.findByIdAndByType();
        String tags = selfVideo.get().getType();

        Gson gson = new Gson();
        List<String> tagList = gson.fromJson(tags, new TypeToken<List<String>>() {
        }.getType());

        List<Pair<VideoDTO,Long>> list = new ArrayList<>();

        for(int i = 0; i < videoList.size(); i++){
            VideoDTO video = videoList.get(i);
            String userTags = video.getType();
            if(StringUtils.isBlank(userTags) || Objects.equals(video.getId(), videoId)){
                continue;
            }
            List<String> videoTagList = gson.fromJson(userTags, new TypeToken<List<String>>() {
            }.getType());
            long distance = AlgorithmUtils.minDistance(tagList, videoTagList);
            list.add(new Pair<>(video, distance));
        }
        List<Pair<VideoDTO,Long>> topUserList = list.stream()
                .sorted((a,b) -> (int)(a.getValue() - b.getValue()))
                .limit(num)
                .collect(Collectors.toList());

        List<UUID> videoIdList = topUserList.stream().map(pair -> pair.getKey().getId()).collect(Collectors.toList());
        List<Video> videoList1 = videoRepository.findByIdForVideoIdList(videoIdList);
        //1,3,2
        //user1,user2,user3
        //1 => user1 , 2 => user2 , 3 => user3
        Map<UUID,List<Video>> userIdUserListMap = videoList1.stream().collect(Collectors.groupingBy(Video::getId));
        List<Video> finalUserList = new ArrayList<>();
        for(UUID videoIds : videoIdList){
            finalUserList.add(userIdUserListMap.get(videoIds).get(0));
        }
        return finalUserList;
    }

    @Override
    public Page<Video> findBySearchTerm(SearchVideoRequest searchVideoRequest) {
        String searchTerm = searchVideoRequest.getSearch();
        // 获取当前页、每页大小、排序字段、排序方式、内容
        long current = searchVideoRequest.getCurrent();
        long size = searchVideoRequest.getPageSize();
        String sortField = searchVideoRequest.getSortField();
        String sortOrder = searchVideoRequest.getSortOrder();
        // 校验size以限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (searchTerm == null || searchTerm.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"搜索条件不能为空");
        }
        // 创建 Sort 对象来实现动态排序
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);

        // 创建 PageRequest 对象来实现分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) size, sort);
        return videoRepository.findBySearchTerm(searchTerm, pageRequest);
    }

    @Override
    public Page<Video> findByType(VideoFilterTypeRequest videoFilterTypeRequest) {

        String channel = videoFilterTypeRequest.getChannel();
        String type = videoFilterTypeRequest.getType();
        String year = videoFilterTypeRequest.getYear();
        Specification<Video> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 如果channel不为空，则添加条件
            if (channel != null && !channel.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("animeType"), channel));
            }

            // 如果year不为空且不等于"全部"，则添加条件
            if (year != null && !year.isEmpty() && !"全部".equals(year)) {
                predicates.add(criteriaBuilder.equal(root.get("date"), year));
            }

            // 如果type不为空且不等于"全部"，则添加条件
            if (type != null && !type.isEmpty() && !"全部".equals(type)) {
                predicates.add(criteriaBuilder.like(root.get("type"), "%" + type + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        long current = videoFilterTypeRequest.getCurrent();
        long size = videoFilterTypeRequest.getPageSize();
        String sortField = videoFilterTypeRequest.getSortField();
        String sortOrder = videoFilterTypeRequest.getSortOrder();
        // 校验size以限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }


        // 创建 Sort 对象来实现动态排序
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);

        // 创建 PageRequest 对象来实现分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) size, sort);
        return videoRepository.findAll(spec, pageRequest);
    }

    @Override
    public List<Video> listVideo() {
        return null;
    }

    @Override
    public List<Video> listVideoForAnimeType(String animeType) {
        return videoRepository.findByTypeList(animeType);
    }

    @Override
    public List<Video> listVideoForAnimeDateInChase(Date animeDate) {
        List<VideoUrl> videoUrls = videoUrlRepository.findByCreateTime(animeDate);
        if (videoUrls != null && !videoUrls.isEmpty()) {
            Set<Video> uniqueVideos = videoUrls.stream()
                    .map(VideoUrl::getVideo)
                    .collect(Collectors.toSet());
            return new ArrayList<>(uniqueVideos); // 将Set转换回List
        }
        return Collections.emptyList();
    }


    private static void getVideoForVideoInfoScoringResponse(List<Video> videoList, ArrayList<VideoInfoScoringResponse> VideoInfoScoringList) {
        for (Video video : videoList){
            Blob photoBlob = video.getPhoto();

            byte[] photoBytes = null;

            if(photoBlob != null){
                try {
                    photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());

                } catch (SQLException e) {
                    throw new BusinessException(ErrorCode.FILE_ERROR,"文件类型错误");
                }

            }
            VideoInfoScoringResponse videoInfoScoringResponse = VideoInfoScoringResponse.builder()
                    .id(video.getId())
                    .alias(video.getAlias())
                    .name(video.getName())
                    .photo(photoBytes)
                    .numberOfSections(video.getNumberOfSections())
                    .totalScore(video.getHotTop())
                    .build();
            VideoInfoScoringList.add(videoInfoScoringResponse);
        }
    }

//    private static void getVideoForVideoInfoScoringResponseScoring(List<VideoInfoScoringDTO> videoList, ArrayList<VideoInfoScoringResponse> VideoInfoScoringList) {
//        for (VideoInfoScoringDTO video : videoList){
//            Blob photoBlob = video.getPhoto();
////            log.info("photoBlob:{}",photoBlob);
////            log.info("photoBlob:{}",videoList);
//            byte[] photoBytes = null;
//
//            if(photoBlob != null){
//                try {
//                    photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
//
//                } catch (SQLException e) {
//                    throw new BusinessException(ErrorCode.FILE_ERROR,"文件类型错误");
//                }
//
//            }
//            VideoInfoScoringResponse videoInfoScoringResponse = VideoInfoScoringResponse.builder()
//                    .id(video.getId())
//                    .alias(video.getAlias())
//                    .name(video.getName())
//                    .photo(photoBytes)
//                    .build();
//            VideoInfoScoringList.add(videoInfoScoringResponse);
//        }
//    }
}


