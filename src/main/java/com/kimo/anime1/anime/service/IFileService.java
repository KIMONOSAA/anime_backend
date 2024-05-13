package com.kimo.anime1.anime.service;

import com.kimo.anime1.anime.model.response.FileForUpdateTopResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {

    String addFile(MultipartFile file);

    List<FileForUpdateTopResponse> getFileForUpdateTop5();
}
