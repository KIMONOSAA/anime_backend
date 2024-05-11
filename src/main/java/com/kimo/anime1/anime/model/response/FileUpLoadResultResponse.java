package com.kimo.anime1.anime.model.response;

import lombok.Data;

/**
 * 动漫过滤器
 * @author <a href="https://github.com/KIMONOSAA“ /> kimo
 */
@Data
public class FileUpLoadResultResponse {
     private String uid;
    // 文件名
    private String name;
    // 状态有：uploading done error removed
    private String status;
    // 服务端响应内容，如：'{"status": "success"}'
    private String response;
}
