package com.kimo.anime1.anime;


import com.kimo.anime1.anime.model.request.comment.CommentsAddRequest;
import com.kimo.anime1.anime.model.response.CommentsPageResponse;
import com.kimo.anime1.anime.service.impl.VideoCommentServerImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@SpringBootTest
public class VideoCommentTest {


    @Autowired
    private VideoCommentServerImpl videoCommentServer;

//    @Test
//    public void videoCommentTest(){
//
//            CommentsAddRequest commentsAddRequest = CommentsAddRequest.builder()
//                    .context("嘤嘤嘤")
//                    .parent(8L)
//                    .videoUrlId(UUID.fromString("7c52cda3-547c-4661-ba03-d92442173c23"))
//                    .userId(1L)
//                    .build();
//            String string = videoCommentServer.addComments(commentsAddRequest);
//            if("评论成功".equals(string)){
//                log.info("测试成功");
//            }else{
//                log.error("测试失败");
//            }
//
//    }

//    @GetMapping("/products/{videoed}")
//    public List<CommentsPage> getAllComments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @PathVariable("videoed") UUID videoId){
//        return videoCommentServer.
//                getAllProducts(PageRequest.of(page, size),videoId);
//    }
//    @Test
//    @Transactional
//    public void getAllCommentsTest() {
//        UUID videoId = UUID.fromString("7c52cda3-547c-4661-ba03-d92442173c23");
//        int page = 0;
//        int size = 5;
//        Long urlId = 1L;
//        List<CommentsPageResponse> allProducts = videoCommentServer.getAllProducts(PageRequest.of(page, size), videoId,urlId);
//        for (CommentsPageResponse commentsPageResponse : allProducts) {
//            System.out.println(commentsPageResponse); // 假设CommentsPage类重写了toString方法
//        }
//    }

//    @Test
//    @Transactional
//    public void getAllCommentsSonTest() {
//        UUID videoId = UUID.fromString("7c52cda3-547c-4661-ba03-d92442173c23");
//        Long parentId = 1L;
//        int page = 0;
//        int size = 5;
//        Long urlId = 1L;
//        List<CommentsPageResponse> allProducts = videoCommentServer.getAllProductsSon(PageRequest.of(page, size), videoId,parentId,urlId);
//        for (CommentsPageResponse commentsPageResponse : allProducts) {
//            System.out.println(commentsPageResponse); // 假设CommentsPage类重写了toString方法
//        }
//    }

//    @GetMapping("/products/{videoed}/{parented}")
//    @Transactional
//    public List<CommentsPage> getAllCommentsSon(@RequestParam(defaultValue = "0") int page,
//                                                @RequestParam(defaultValue = "10") int size,
//                                                @PathVariable("videoed") UUID videoId,
//                                                @PathVariable("parented") Long parentId){
//        PageRequest pageRequest = PageRequest.of(page, size);
//        return videoCommentServer.
//                getAllProductsSon(pageRequest,videoId,parentId);
//    }
}
