package com.kimo.anime1.anime.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kimo.anime1.anime.model.entity.User;

/**
 * 用户仓库
 *
 * @author  kimo
 */
public interface UserRepository extends JpaRepository<User, UUID>{

    Optional<User> findByEmail(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.username = :username")
    long countByUsername(String username);


    User findByUsername(String userEmail);
}
