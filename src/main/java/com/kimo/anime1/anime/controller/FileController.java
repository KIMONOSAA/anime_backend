package com.kimo.anime1.anime.controller;


import com.kimo.anime1.anime.comment.BaseResponse;
import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.enums.ResultsUtils;
import com.kimo.anime1.anime.model.response.FileForUpdateTopResponse;
import com.kimo.anime1.anime.model.response.FileUpLoadResultResponse;
import com.kimo.anime1.anime.service.impl.FileServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    /**
     * 创建
     *
     * @param file
     * @param
     * @return
     */
    @PostMapping("/add")
    @Transactional
    public BaseResponse<String> addFile(@RequestParam("file") MultipartFile file) throws IOException {
        String isVail = fileService.addFile(file);
        if(isVail.equals("提交成功")){
            return ResultsUtils.success(isVail);
        }else{
            return ResultsUtils.error(ErrorCode.FILE_ERROR,"文件提交失败");
        }

    }

    /**
     * 获取时间前5的字段
     *
     * @param
     * @param
     * @return
     */
    @GetMapping("/get/file")
    @Transactional
    public BaseResponse<List<FileForUpdateTopResponse>> getFile()  {
        List<FileForUpdateTopResponse> fileUpLoadResultResponses = fileService.getFileForUpdateTop5();
        return ResultsUtils.success(fileUpLoadResultResponses);
    }
    
    
}
