package com.kimo.anime1.anime.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import com.kimo.anime1.anime.comment.BaseResponse;
import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.enums.ResultsUtils;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.dto.AccessTokenDTO;
import com.kimo.anime1.anime.model.dto.MatchUsersDTO;
import com.kimo.anime1.anime.model.request.video.*;
import com.kimo.anime1.anime.model.request.video.AccessTokenVideoUrlRequest;
import com.kimo.anime1.anime.model.request.video.VideoAnimeTypeRequest;
import com.kimo.anime1.anime.model.request.video.VideoInfoDateRequest;
import com.kimo.anime1.anime.model.response.*;
import com.kimo.anime1.anime.service.IVideoUrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.kimo.anime1.anime.model.entity.Video;
import com.kimo.anime1.anime.model.entity.VideoUrl;
import com.kimo.anime1.anime.service.impl.VideoVideoFileService;
import com.kimo.anime1.anime.service.IVideoService;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 视频控制器
 *
 * @author  kimo
 */
@Slf4j
@Transactional
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoVideoFileService videoFileService;

    @Autowired
    private IVideoService iVideoService;

    @Autowired
    private IVideoUrlService videoUrlService;


    /**
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/add/url")
    public BaseResponse<FileUpLoadResultResponse> upload(VideoUploadRequest videoUploadRequest)
             {
        UUID uuid = videoUploadRequest.getVideoId();
        MultipartFile uploadFile = videoUploadRequest.getUploadFile();
        Integer section = videoUploadRequest.getSection();
        FileUpLoadResultResponse videoUpdate = videoFileService.upload(uploadFile, uuid,section);
        if("success".equals(videoUpdate.getResponse())){
            return ResultsUtils.success(videoUpdate);
        }else{
            return ResultsUtils.error(ErrorCode.VIDEO_ERROR,"视频上传失败");
        }

    }

//    //根据文件名删除oss上的文件
//    @DeleteMapping("file/delete")
//    public FileUpLoadResult delete(@RequestParam("fileName") String objectName)
//            throws Exception {
//        return this.fileService.delete(objectName);
//    }

//    //查询oss上的所有文件
//    @GetMapping("file/list")
//    public List<OSSObjectSummary> list()
//            throws Exception {
//        return this.fileService.list();
//    }

//    //根据文件名下载oss上的文件
//    @PostMapping("file/download")
//    public ResponseEntity<byte[]> download(@RequestParam("fileName") String objectName) throws IOException {
//        HttpHeaders headers = new HttpHeaders();
////         以流的形式下载文件,这样可以实现任意格式的文件下载
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
////         文件名包含中文时乱码问题
//    //    headers.setContentDispositionFormData("attachment", new String(exportFile.getName().getBytes("utf-8"), "ISO8859-1"));
//        headers.setContentDispositionFormData("attachment", objectName);
//        InputStream is = this.fileService.exportOssFile(objectName);
//        return new ResponseEntity<byte[]>(is.toString().getBytes(),
//                headers, HttpStatus.CREATED);
//    }

    /**
     * 上传视频

     * @return 上传结果 code 0 表示上传成功
     */
    //@RequestParam("name") String videoName,@RequestParam("alias") String videoAlias,@RequestParam("type") String videoType, @RequestParam("description") String videoDescription,@RequestParam("numberOfSections") int videoNumberOfSections, @RequestParam("animeCompany") String videoAnimeCompany ,@RequestParam("directorName") String videoDirectorName, @RequestParam("comicBookAuthor") String videoComicBookAuthor, @RequestParam("voiceActor") String videoVoiceActor,@RequestParam("date") String date,@RequestParam("animeType") String animeType,@RequestParam("photo") MultipartFile photoFile
    @PostMapping("/add/video")
    @Transactional
    public BaseResponse<String> videoUpload( VideoInfoAddRequest videoInfoAddRequest
                                            ){
        if (videoInfoAddRequest == null){
            return ResultsUtils.error(ErrorCode.NULL_ERROR,"参数不能为空");
        }
        String videoName = videoInfoAddRequest.getVideoName();
        String videoAlias = videoInfoAddRequest.getVideoAlias();
        String videoType = videoInfoAddRequest.getVideoType();
        String videoDescription = videoInfoAddRequest.getVideoDescription();
        int videoNumberOfSections = videoInfoAddRequest.getVideoNumberOfSections();
        String videoAnimeCompany = videoInfoAddRequest.getVideoAnimeCompany();
        String videoDirectorName = videoInfoAddRequest.getVideoDirectorName();
        String videoComicBookAuthor = videoInfoAddRequest.getVideoComicBookAuthor();
        String videoVoiceActor = videoInfoAddRequest.getVideoVoiceActor();
        String date = videoInfoAddRequest.getDate();
        String animeType = videoInfoAddRequest.getAnimeType();
        MultipartFile photoFile = videoInfoAddRequest.getPhotoFile();
        String[] voiceActorArray = videoVoiceActor.split(",");
        List<String> voicActorList = Arrays.stream(voiceActorArray).collect(Collectors.toList());
        String[] animeTypeArray = videoType.split(",");
        List<String> animeTypeList = Arrays.stream(animeTypeArray).collect(Collectors.toList());                                

        iVideoService.addVideo(videoName,videoAlias,animeTypeList,videoDescription,videoNumberOfSections, videoAnimeCompany,videoDirectorName,videoComicBookAuthor,voicActorList,date,animeType, photoFile);

        return ResultsUtils.success("视频上传成功");
    }




    /**
     * 获取所有视频的数据不包含视频
     * @return 所有数据
     */
    @PostMapping("/list/allVideo")
    @Transactional
    public BaseResponse<List<VideoTitleResponse>> getAllAlias(@RequestBody VideoFilterTypeRequest pageRequest){

        Page<Video> videos = iVideoService.findByType(pageRequest);
        List<VideoTitleResponse> alias = null;
        
        alias = videos.stream().map(this::getVideoTitleResponse).collect(Collectors.toList());

        return ResultsUtils.success(alias);
    }

    /**
     * 根据类型搜索查找所以视频，注意不是url
     * @param listItem
     * @return
     */
    @PostMapping("/videoType")
    @Transactional
    public BaseResponse<List<VideoTitleResponse>> filtersVideoData(@RequestBody VideoFilterTypeRequest listItem){
        if(listItem == null){
            return ResultsUtils.error(ErrorCode.NULL_ERROR,"参数不能为空");
        }

        Page<Video> videos = iVideoService.findByType(listItem);
        List<VideoTitleResponse> alias = null;

        alias = videos.stream().map(this::getVideoTitleResponse).collect(Collectors.toList());
        return ResultsUtils.success(alias);
    }

    /**
     * 获取指定年份的所以视频数据
     * @return 所有数据
     */
    //忘记写这个是干嘛的了，因为前端好像没有写这个接口
    @GetMapping("/list/allVideoInYear")
    @Transactional
    public BaseResponse<List<VideoTitleResponse>> getVideoAllYears(@RequestBody VideoAllYearsRequest allYearsRequest){
        Page<Video> videos = iVideoService.findByData(allYearsRequest);
        List<VideoTitleResponse> videoTitleResponse =  videos.stream()
                                                        .map(this::getVideoTitleResponse)
                                                        .collect(Collectors.toList());

        return ResultsUtils.success(videoTitleResponse);

    }



    /**
     * 获取指定UUID视频和集数的加密视频地址
     * @param uuid
     * @param videoId
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping("/getVideo/{uuid}/{videoId}")
    @Transactional
    public BaseResponse<AccessTokenDTO> getEncryptedVideoUrlByUuidAndVideoId(@PathVariable UUID uuid, @PathVariable Long videoId, HttpServletRequest request) throws IOException {
        if(uuid == null){
            return ResultsUtils.error(ErrorCode.NULL_ERROR,"参数不能为空");
        }
        if (videoId == null || videoId <= 0){
            return ResultsUtils.error(ErrorCode.NULL_ERROR,"参数不能为空");
        }
        AccessTokenDTO accessTokenDTO = videoUrlService.findByIdAndVideo(uuid, videoId, request);

        return ResultsUtils.success(accessTokenDTO);
    }


    /**
     * 请求token验证解密获取真实视频地址 说实话这个方法以及上面那个方法在我的需求上一点用都没有 嘛不过嫩用到ConcurrentHashMap已经满足了
     * @param accessTokenVideoUrlRequest
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws ClassNotFoundException
     */
    @PostMapping("/getVideoUrl")
    @Transactional
    public BaseResponse<String> getVideoAndDecryptObject(@RequestBody AccessTokenVideoUrlRequest accessTokenVideoUrlRequest) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, ClassNotFoundException {
        if(accessTokenVideoUrlRequest == null){
            return ResultsUtils.error(ErrorCode.NULL_ERROR,"参数不能为空");
        }

        Object urlForToken = videoUrlService.getUrlForToken(accessTokenVideoUrlRequest);
        if(urlForToken == null){
            return ResultsUtils.error(ErrorCode.VIDEO_ERROR,"没有找到这个视频");
        }
        return ResultsUtils.success(urlForToken.toString());
    }



    /**
     * 获取当前视频的信息
     * @param
     * @return
     */
    //todo要改url
    @GetMapping("/getVideoResponse/{videoId}")
    @Transactional
    public BaseResponse<VideoInfoResponse> getVideoResponseInfoById(@PathVariable UUID videoId){
        Video video = iVideoService.getVideo(videoId).orElseThrow(() -> new BusinessException(ErrorCode.VIDEO_ERROR, "没有找到这个视频"));
        Blob photo = video.getPhoto();
        byte[] photoBytes = null;
        
        try {
            photoBytes = photo.getBytes(1, (int)photo.length());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FILE_ERROR,"文件类型错误");
        }
            
        

        VideoInfoResponse info = VideoInfoResponse.builder()
                                .alias(video.getAlias())
                                .animeCompany(video.getAnimeCompany())
                                .comicBookAuthor(video.getComicBookAuthor())
                                .date(video.getDate())
                                .description(video.getDescription())
                                .directorName(video.getDirectorName())
                                .photo(photoBytes)
                                .id(video.getId())
                                .type(Collections.singletonList(video.getType()))
                                .voiceActor(Collections.singletonList(video.getVoiceActor()))
                                .name(video.getName())
                                .build();

        return ResultsUtils.success(info);
                                
    }


    /**
     * 删除视频
     * @param videoId
     * @return
     */
    @DeleteMapping("/deleteVideo/{videoId}")
    @Transactional
    public BaseResponse<String> deleteVideoResponseById(@PathVariable UUID videoId){
        // 尝试获取视频，如果视频不存在，get()方法会抛出异常
        Optional<Video> optionalVideo = iVideoService.getVideo(videoId);
        if (!optionalVideo.isPresent()) {
            // 如果视频不存在，直接返回错误响应
            return ResultsUtils.error(ErrorCode.VIDEO_NOT_FOUND, "视频不存在");
        }

        Video video = optionalVideo.get(); // 现在可以安全地调用get()，因为我们知道它包含值
        iVideoService.deleteVideoById(video.getId());
        return ResultsUtils.success("视频删除成功");
    }


    /**
     * 更新视频信息
     * @param video
     * @param videoId
     * @return
     */
    @PutMapping("/updated/{videoId}")
    @Transactional
    public BaseResponse<?> updateVideoResponse(@RequestBody VideoUpdateRequest video, @PathVariable UUID videoId ){
        if(video == null || videoId == null){
            return ResultsUtils.error(ErrorCode.VIDEO_ERROR,"参数不能为空");
        }
        Video videoz = iVideoService.getVideo(videoId).get();
        if(videoz.getId() == null){
            return ResultsUtils.error(ErrorCode.VIDEO_ERROR,"没有找到这个视频id");
        }
        VideoUpdateRequest updateVideo = iVideoService.updatedVideoById(video.getId(), video);
        return ResultsUtils.success(updateVideo);

    }

    /**
     * 根据数据搜索
     * @return
     */
    @PostMapping("/search")
    @Transactional
    public BaseResponse<List<VideoTitleResponse>> searchVideo(@RequestBody SearchVideoRequest searchVideoRequest){
        Page<Video> videos = iVideoService.findBySearchTerm(searchVideoRequest);
        List<VideoTitleResponse> alias = null;

        alias = videos.stream().map(this::getVideoTitleResponse).collect(Collectors.toList());
        return ResultsUtils.success(alias);
    }





    /**
     * 获取前20热度高的动漫列表
     * @param
     * @return
     */
    @GetMapping("/getVideoHop")
    @Transactional
    public BaseResponse<List<VideoInfoScoringResponse>> getVideoView(){
        return ResultsUtils.success(iVideoService.getAllvideoHotop());
    }

    /**
     * 获取前20评分最高的动漫列表
     * @param
     * @return
     */
    @GetMapping("/getVideoScore")
    @Transactional
    public BaseResponse<List<VideoInfoScoringResponse>> getVideoScore(){
        List<VideoInfoScoringResponse> videoInfoScoringResponses = iVideoService.getAllVideoScores();
        return ResultsUtils.success(videoInfoScoringResponses);
    }


    /**
     * 最推荐的动漫
     * @return
     */

    @PostMapping("/match")
    @Transactional
    public BaseResponse<List<VideoInfoResponse>> matchUsers(@RequestBody MatchUsersDTO matchUsersDTO){
        if(matchUsersDTO.getNum() <= 0 || matchUsersDTO.getNum() > 20){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<Video> videos = iVideoService.matchUsers(matchUsersDTO.getNum(), matchUsersDTO.getVideoId());
        List<VideoInfoResponse> videoList = videos.stream().map(this::reverseSequence).collect(Collectors.toList());
        return ResultsUtils.success(videoList);
    }



    /**
     * 获取指定类型的动漫
     * @param videoAnimeTypeRequest
     * @return
     */
    @PostMapping("/listVideoAnimeType")
    @Transactional
    public BaseResponse<List<VideoInfoResponse>> listVideoForAnimeType(@RequestBody VideoAnimeTypeRequest videoAnimeTypeRequest){
        List<Video> videos = iVideoService.listVideoForAnimeType(videoAnimeTypeRequest.getAnimeType());
        List<VideoInfoResponse> videoList = videos.stream().map(this::reverseSequence).toList();

        return ResultsUtils.success(videoList);
    }


    /**
     * 获取一周追番时间表的动漫
     * @param videoInfoDateRequest
     * @return
     */
    @PostMapping("/getAllChase")
    @Transactional
    public BaseResponse<List<VideoInfoResponse>> getAllChase(@RequestBody VideoInfoDateRequest videoInfoDateRequest){
        List<Video> videos = iVideoService.listVideoForAnimeDateInChase(videoInfoDateRequest.getAnimeDate());
        List<VideoInfoResponse> videoList = videos.stream().map(this::reverseSequence).collect(Collectors.toList());
        return ResultsUtils.success(videoList);
    }
    /**
     * 获取指定UUID视频的所有集数
     * @return
     */
    @GetMapping("/getVideoSection/{uuid}")
    public BaseResponse<List<SectionUrlIdResponse>> getVideoUrl(@PathVariable UUID uuid){
        if(uuid == null){
            return ResultsUtils.error(ErrorCode.NULL_ERROR,"参数不能为空");
        }
        List<VideoUrl> videoUrlList = iVideoService.findById(uuid).orElseThrow(() -> new BusinessException(ErrorCode.VIDEO_ERROR, "没有找到这个视频")).getVideoUrl();
        return ResultsUtils.success(videoUrlList.stream().map(this::getSectionAndUrlId).toList());
    }



    public SectionUrlIdResponse getSectionAndUrlId(VideoUrl videoUrl) {
        if (videoUrl == null){
            throw new BusinessException(ErrorCode.VIDEO_ERROR,"没有找到这个视频");
        }
        return SectionUrlIdResponse.builder()
                .UrlId(videoUrl.getId())
                .SectionId(videoUrl.getSection())
                .build();
    }

    
    public VideoTitleResponse getVideoTitleResponse(Video video){
        Blob photoBlob = video.getPhoto();
        byte[] photoBytes = null;
        try {
            photoBytes = photoBlob.getBytes(1, (int)photoBlob.length());

        } catch (SQLException e) {
            throw new BusinessException(ErrorCode.FILE_ERROR,"文件类型错误");
        }

        return VideoTitleResponse.builder()
                                    .id(video.getId())
                                    .photo(photoBytes)
                                    .alias(video.getAlias())
                                    .description(video.getDescription())
                                    .type(Collections.singletonList(video.getType()))
                                    .name(video.getName())
                                    .build();
    }

    public VideoInfoResponse reverseSequence(Video video){

        Blob photoBlob = video.getPhoto();
        
        byte[] photoBytes = null;
        
            if(photoBlob != null){
                try {
                    photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
                    
                } catch (SQLException e) {
                    throw new BusinessException(ErrorCode.FILE_ERROR,"文件类型错误");
                }
                
            }
        return VideoInfoResponse.builder()
                            .id(video.getId())
                            .photo(photoBytes)
                            .alias(video.getAlias())
                            .description(video.getDescription())
                            .animeCompany(video.getAnimeCompany())
                            .comicBookAuthor(video.getComicBookAuthor())
                            .date(video.getDate())
                            .directorName(video.getDirectorName())
                            .name(video.getName())
                            .type(Collections.singletonList(video.getType()))
                            .voiceActor(Collections.singletonList(video.getVoiceActor())).build();

    }




}



