package com.kimo.anime1.anime.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.kimo.anime1.anime.model.response.FileUpLoadResultResponse;


/**
 * 文件服务
 * @author  kimo
 */
public interface IFileService {
    FileUpLoadResultResponse upload(MultipartFile file, UUID uuid, Integer section);

    FileUpLoadResultResponse delete(String objectName);
}
