package com.kimo.anime1.anime.service.impl;

import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.entity.File;
import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.model.entity.UserFile;
import com.kimo.anime1.anime.model.entity.Video;
import com.kimo.anime1.anime.model.response.FileForUpdateTopResponse;
import com.kimo.anime1.anime.model.response.FileUpLoadResultResponse;
import com.kimo.anime1.anime.model.response.UserFIleResponse;
import com.kimo.anime1.anime.model.response.VideoResponse;
import com.kimo.anime1.anime.repository.FileRepository;
import com.kimo.anime1.anime.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public String addFile(MultipartFile file) {

        Blob fileUpload = null;
        try {
            if (file != null && !file.isEmpty()) {
                byte[] bytes = file.getBytes();
                fileUpload = new SerialBlob(bytes);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.FILE_ERROR, "文件类型错误");
        }

        File file1 = new File();
        file1.setBanner(fileUpload);
        fileRepository.save(file1);
        return "提交成功";
    }

    @Override
    public List<FileForUpdateTopResponse> getFileForUpdateTop5() {
        List<File> files = fileRepository.findAllForUpdateTime();
        return files.stream().map(this::getFileResponse).collect(Collectors.toList());
    }

    public FileForUpdateTopResponse getFileResponse(File file){

        Blob photoBlob = file.getBanner();

        byte[] photoBytes = null;

        if(photoBlob != null){
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new BusinessException(ErrorCode.FILE_ERROR,"文件类型错误");
            }

        }

        return FileForUpdateTopResponse.builder()
                .file(photoBytes)
                .build();


    }

}
