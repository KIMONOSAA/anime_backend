package com.kimo.anime1.anime.repository;

import com.kimo.anime1.anime.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    @Query("SELECT f FROM File f ORDER BY f.updateTime DESC Limit 5")
    List<File> findAllForUpdateTime();

}
