package com.kimo.anime1.anime.service;

import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.model.request.scoring.ScoringRequest;
import com.kimo.anime1.anime.model.response.ScoringPageResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

/**
 * 视频评分服务
 * @Author  kimo
 */
public interface IVideoScoringService {

    String addComments(ScoringRequest scoringRequest, User user);

    String updateComments(ScoringRequest scoringRequest, Long id);

    String deletedComments(Long id);

    List<ScoringPageResponse> getAllProducts(PageRequest of, UUID videoId);

}
