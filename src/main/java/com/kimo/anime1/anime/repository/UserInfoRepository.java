package com.kimo.anime1.anime.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimo.anime1.anime.model.entity.UserInfo;

/**
 *
 * @author  kimo
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    
}
