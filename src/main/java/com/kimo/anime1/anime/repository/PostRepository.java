package com.kimo.anime1.anime.repository;

import com.kimo.anime1.anime.model.entity.Post;
import com.kimo.anime1.anime.model.entity.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 帖子仓库类
 * @author  kimo
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> , JpaSpecificationExecutor<Post> {
}