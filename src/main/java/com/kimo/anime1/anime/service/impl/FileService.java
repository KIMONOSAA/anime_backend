package com.kimo.anime1.anime.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.repository.VideoRepository;
import com.kimo.anime1.anime.service.IFileService;
import com.kimo.anime1.anime.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.kimo.anime1.anime.config.OSSConfig;
import com.kimo.anime1.anime.model.entity.Video;
import com.kimo.anime1.anime.model.entity.VideoUrl;
import com.kimo.anime1.anime.repository.VideoUrlRepository;
import com.kimo.anime1.anime.model.response.FileUpLoadResultResponse;

/**
 * 文件服务
 * @Author  kimo
 */
@Service
public class FileService implements IFileService {

    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png",".mp4"};
    @Autowired
    private OSS ossClient;

    @Autowired
    private OSSConfig aliyunConfig;

    @Autowired
    private IVideoService videoService;

    @Autowired
    private VideoUrlRepository videoUrlRepository;

    @Autowired
    private VideoRepository videoRepository;



    @Override
    public FileUpLoadResultResponse upload(MultipartFile uploadFile, UUID uuid, Integer section) {
        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(),
                    type)) {
                isLegal = true;
                break;
            }
        }
        Optional<Video> video = videoService.getVideo(uuid);
        //封装Result对象，并且将文件的byte数组放置到result对象中
        FileUpLoadResultResponse fileUploadResultResponse = new FileUpLoadResultResponse();
        if (video.isEmpty() && !isLegal) {
            throw new BusinessException(ErrorCode.VIDEO_ERROR,"没有这个视频或者文件格式不正确");
//            fileUploadResult.setStatus("error");
//            return fileUploadResult;
        }
        //文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        // 上传到阿里云
        try {
            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new
                    ByteArrayInputStream(uploadFile.getBytes()));

        } catch (Exception e) {
            //上传失败
            throw new BusinessException(ErrorCode.VIDEO_ERROR,"没有这个视频或者文件格式不正确");
        }
        fileUploadResultResponse.setStatus("done");
        fileUploadResultResponse.setResponse("success");
        //this.aliyunConfig.getUrlPrefix() + filePath 文件路径需要保持数据库
         Video video1 = video.get();
        LocalDateTime now = LocalDateTime.now();
        // 转换为ZonedDateTime，以便我们可以有时区信息
        java.time.ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
        // 转换为Instant，它表示时间线上的一个瞬间点
        java.time.Instant instant = zonedDateTime.toInstant();
        // 使用Instant和默认时区来创建Date对象
        Date updateTime = Date.from(instant);
        video1.setUpdateTime(updateTime);
        fileUploadResultResponse.setName(this.aliyunConfig.getUrlPrefix() + filePath);
        fileUploadResultResponse.setUid(String.valueOf(System.currentTimeMillis()));

        VideoUrl videoUrl = VideoUrl.builder()
                                            .url(this.aliyunConfig.getUrlPrefix() + filePath)
                                            .video(video.get())
                                            .isDelete(0)
                                            .section(section)
                                            .build();

        videoUrlRepository.save(videoUrl);
        videoRepository.save(video1);
        return fileUploadResultResponse;
    }

    private String getFilePath(String sourceFileName) {
        LocalDateTime now = LocalDateTime.now();
        String dateTimePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
        int randomNum = new Random().nextInt(9999 - 100) + 100; // 生成100到9999之间的随机数
        return "video/" + dateTimePath + System.currentTimeMillis() +
                randomNum + "." + org.apache.commons.lang3.StringUtils.substringAfterLast(sourceFileName, ".");
    }

    public List<OSSObjectSummary> list() {
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliyunConfig.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }
    //删除文件
    public FileUpLoadResultResponse delete(String objectName) {
        ossClient.deleteObject(aliyunConfig.getBucketName(), objectName);
        FileUpLoadResultResponse fileUploadResultResponse = new FileUpLoadResultResponse();
        fileUploadResultResponse.setName(objectName);
        fileUploadResultResponse.setStatus("removed");
        fileUploadResultResponse.setResponse("success");
        return fileUploadResultResponse;
    }
    //下载文件
    public InputStream exportOssFile(String objectName) {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(aliyunConfig.getBucketName(), objectName);
        // 读取文件内容。
        InputStream is = ossObject.getObjectContent();
        return is;
    }


    private String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }
}
