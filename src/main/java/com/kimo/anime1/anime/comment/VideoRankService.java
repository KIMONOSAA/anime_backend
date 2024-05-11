package com.kimo.anime1.anime.comment;

import com.kimo.anime1.anime.repository.VideoRepository;
import com.kimo.anime1.anime.repository.VideoScoringServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.kimo.anime1.anime.contant.RedisContants.STATS;

/**
 * 视频的综合热度计算以及定时任务
 * @author  kimo
 */
@Service
public class VideoRankService {

    private final double uv_percentage = 50.00;
    private final double score_percentage = 30.00;
    private final double comments_percentage = 20.00;



    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoScoringServiceRepository videoScoringServiceRepository;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0 24 0 * * ?")
    public void updateVideoUV() {
        videoRepository.findAll().forEach(video -> {
            long count = video.getComments().stream().count();
            double avg = videoScoringServiceRepository.calculateAverageScoreByVideoId(video.getId());
            Long uv = stringRedisTemplate.opsForHyperLogLog().size(STATS + video.getId());
            double hotTop = (uv * uv_percentage) + (avg * score_percentage) + (count * comments_percentage);
            video.setVideouv(uv);
            video.setHotTop(hotTop);
            videoRepository.save(video);
        });


    }
}
