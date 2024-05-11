package com.kimo.anime1.anime.controller;


import com.kimo.anime1.anime.comment.*;
import com.kimo.anime1.anime.enums.ResultsUtils;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.entity.Post;
import com.kimo.anime1.anime.model.request.post.PostAddRequest;
import com.kimo.anime1.anime.model.request.post.PostQueryRequest;
import com.kimo.anime1.anime.model.request.post.PostUpdateRequest;
import com.kimo.anime1.anime.repository.PostRepository;
import com.kimo.anime1.anime.service.PostService;
import com.kimo.anime1.anime.service.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;


/**
 * 帖子接口
 * 前端暂未写
 * @author  kimo
 */
@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;


    @Autowired
    private PostRepository postRepository;

    // region 增删改查

    /**
     * 创建
     *
     * @param postAddRequest
     * @param
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addPost(@RequestBody PostAddRequest postAddRequest) {
        UUID id = UseIdHolder.getUserForToken().getId();
        if (postAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Post post = new Post();
        BeanUtils.copyProperties(postAddRequest, post);
        // 校验
        postService.validPost(post, true);
        post.setUserId(id);
        Post postSave = postRepository.save(post);
        if(postSave == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long newPostId = post.getId();
        return ResultsUtils.success(newPostId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deletePost(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        UUID userId = UseIdHolder.getUserForToken().getId();
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        Post oldPost = postRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        if (oldPost == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldPost.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        postRepository.deleteById(id);
        return ResultsUtils.success(true);
    }

    /**
     * 更新
     *
     * @param postUpdateRequest
     * @param
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updatePost(@RequestBody PostUpdateRequest postUpdateRequest) {
        UUID userId = UseIdHolder.getUserForToken().getId();
        if (postUpdateRequest == null || postUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Post post = new Post();
        BeanUtils.copyProperties(postUpdateRequest, post);
        // 参数校验
        postService.validPost(post, false);
        long id = postUpdateRequest.getId();
        // 判断是否存在
        Post oldPost = postRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        if (oldPost == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldPost.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        postRepository.save(post);
        return ResultsUtils.success(true);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Post> getPostById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        return ResultsUtils.success(post);
    }


    @GetMapping("/list/page")
    public BaseResponse<Page<Post>> listPostByPage(PostQueryRequest postQueryRequest, HttpServletRequest request) {
        if (postQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 获取当前页、每页大小、排序字段、排序方式、内容
        long current = postQueryRequest.getCurrent();
        long size = postQueryRequest.getPageSize();
        String sortField = postQueryRequest.getSortField();
        String sortOrder = postQueryRequest.getSortOrder();
        String content = postQueryRequest.getContent();

        // 校验size以限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Specification<Post> specification = (root, query, criteriaBuilder) -> {
            if (content != null && !content.isEmpty()) {
                return criteriaBuilder.like(root.get("content"), "%" + content + "%");
            }
            return null;
        };

        // 创建 Sort 对象来实现动态排序
        Sort sort = Sort.by(Sort.Direction.valueOf(sortOrder), sortField);

        // 创建 PageRequest 对象来实现分页
        PageRequest pageRequest = PageRequest.of((int) current, (int) size, sort);

        // 使用 JpaSpecificationExecutor 执行查询，返回分页结果
        Page<Post> all = postRepository.findAll(specification, pageRequest);

        return ResultsUtils.success(all);
    }

    /**
     * （管理员）对文章帖子进行审核通过
     */
    @PostMapping("/check")
    public BaseResponse<Boolean> checkPost(@RequestBody CheckRequest checkRequest) {
        if (checkRequest == null || checkRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = checkRequest.getId();
        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        post.setReviewStatus(1);
        postRepository.save(post);
        return ResultsUtils.success(true);
    }
    /**
     * （管理员）对文章帖子进行审核拒绝
     */
    @PostMapping("/reject")
    public BaseResponse<Boolean> rejectPost(@RequestBody CheckRequest checkRequest) {
        if (checkRequest == null || checkRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = checkRequest.getId();
        Post post = postRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ERROR));
        if (post == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        post.setReviewStatus(2);
        postRepository.save(post);
        return ResultsUtils.success(true);
    }

}
