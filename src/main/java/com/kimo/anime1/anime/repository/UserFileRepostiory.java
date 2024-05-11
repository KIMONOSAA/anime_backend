package com.kimo.anime1.anime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimo.anime1.anime.model.entity.UserFile;

/**
 * 用户文件仓库
 * @author  kimo
 */
public interface UserFileRepostiory extends JpaRepository<UserFile,Long> {
    
}
