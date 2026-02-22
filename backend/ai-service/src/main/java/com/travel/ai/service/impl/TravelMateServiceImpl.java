package com.travel.ai.service.impl;

import com.travel.ai.dao.RecruitPostDao;
import com.travel.ai.dao.TravelMateMatchDao;
import com.travel.ai.entity.RecruitPost;
import com.travel.ai.entity.TravelMateMatch;
import com.travel.ai.service.TravelMateService;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TravelMateServiceImpl implements TravelMateService {

    @Resource
    private TravelMateMatchDao travelMateMatchDao;

    @Resource
    private RecruitPostDao recruitPostDao;

    @Override
    public List<TravelMateMatch> matchTravelMates(Long userId, String destination, String travelDate, String travelStyle, String interests) {
        // 模拟匹配结果，实际项目中应该从数据库查询并计算匹配度
        List<TravelMateMatch> matches = new ArrayList<>();

        // 创建模拟数据
        TravelMateMatch match1 = new TravelMateMatch();
        match1.setUserId(userId);
        match1.setMatchedUserId(2L);
        match1.setDestination(destination);
        match1.setTravelDate(travelDate);
        match1.setTravelStyle(travelStyle);
        match1.setInterests(interests);
        match1.setMatchScore(95);
        match1.setStatus("PENDING");
        match1.setCreatedAt(LocalDateTime.now());
        match1.setUpdatedAt(LocalDateTime.now());
        matches.add(match1);

        TravelMateMatch match2 = new TravelMateMatch();
        match2.setUserId(userId);
        match2.setMatchedUserId(3L);
        match2.setDestination(destination);
        match2.setTravelDate(travelDate);
        match2.setTravelStyle(travelStyle);
        match2.setInterests(interests);
        match2.setMatchScore(88);
        match2.setStatus("PENDING");
        match2.setCreatedAt(LocalDateTime.now());
        match2.setUpdatedAt(LocalDateTime.now());
        matches.add(match2);

        TravelMateMatch match3 = new TravelMateMatch();
        match3.setUserId(userId);
        match3.setMatchedUserId(4L);
        match3.setDestination(destination);
        match3.setTravelDate(travelDate);
        match3.setTravelStyle(travelStyle);
        match3.setInterests(interests);
        match3.setMatchScore(92);
        match3.setStatus("PENDING");
        match3.setCreatedAt(LocalDateTime.now());
        match3.setUpdatedAt(LocalDateTime.now());
        matches.add(match3);

        // 保存匹配结果到数据库
        matches.forEach(travelMateMatchDao::save);

        return matches;
    }

    @Override
    public PageResult<TravelMateMatch> getUserMatches(Long userId, PageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getSize());
        Page<TravelMateMatch> result = travelMateMatchDao.findAll(pageRequest);
        List<TravelMateMatch> filteredMatches = result.getContent().stream()
                .filter(match -> match.getUserId().equals(userId))
                .collect(Collectors.toList());
        return new PageResult<>((long) filteredMatches.size(), query.getSize(), query.getPage(), filteredMatches);
    }

    @Override
    public RecruitPost createRecruitPost(RecruitPost recruitPost) {
        recruitPost.setCurrentMembers(1);
        recruitPost.setStatus("ACTIVE");
        recruitPost.setCreatedAt(LocalDateTime.now());
        recruitPost.setUpdatedAt(LocalDateTime.now());
        return recruitPostDao.save(recruitPost);
    }

    @Override
    public PageResult<RecruitPost> getRecruitPosts(PageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getSize());
        Page<RecruitPost> result = recruitPostDao.findAll(pageRequest);
        List<RecruitPost> activePosts = result.getContent().stream()
                .filter(post -> "ACTIVE".equals(post.getStatus()))
                .collect(Collectors.toList());
        return new PageResult<>((long) activePosts.size(), query.getSize(), query.getPage(), activePosts);
    }

    @Override
    public RecruitPost getRecruitPostById(Long id) {
        return recruitPostDao.findById(id).orElse(null);
    }

    @Override
    public RecruitPost updateRecruitPost(RecruitPost recruitPost) {
        recruitPost.setUpdatedAt(LocalDateTime.now());
        return recruitPostDao.save(recruitPost);
    }

    @Override
    public void deleteRecruitPost(Long id) {
        recruitPostDao.deleteById(id);
    }

    @Override
    public PageResult<RecruitPost> searchRecruitPosts(String keyword, PageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getSize());
        Page<RecruitPost> result = recruitPostDao.findAll(pageRequest);
        List<RecruitPost> filteredPosts;
        
        // 如果keyword为null，则返回所有活跃的招募帖
        if (keyword == null || keyword.isEmpty()) {
            filteredPosts = result.getContent().stream()
                    .filter(post -> "ACTIVE".equals(post.getStatus()))
                    .collect(Collectors.toList());
        } else {
            // 如果keyword不为null，则根据keyword过滤招募帖
            filteredPosts = result.getContent().stream()
                    .filter(post -> "ACTIVE".equals(post.getStatus()) && 
                            (post.getDestination().contains(keyword) || 
                            (post.getDescription() != null && post.getDescription().contains(keyword)) || 
                            (post.getTags() != null && post.getTags().contains(keyword))))
                    .collect(Collectors.toList());
        }
        
        return new PageResult<>((long) filteredPosts.size(), query.getSize(), query.getPage(), filteredPosts);
    }

    @Override
    public Map<String, Object> getMatchAnalysis(Long userId) {
        // 获取用户的所有匹配记录
        List<TravelMateMatch> matches = travelMateMatchDao.findAll().stream()
                .filter(match -> match.getUserId().equals(userId))
                .collect(Collectors.toList());

        // 分析匹配数据
        Map<String, Object> analysis = new HashMap<>();

        if (!matches.isEmpty()) {
            // 计算平均匹配分数
            double averageScore = matches.stream()
                    .mapToInt(TravelMateMatch::getMatchScore)
                    .average()
                    .orElse(0);

            // 按目的地分组
            Map<String, Long> destinationCounts = matches.stream()
                    .collect(Collectors.groupingBy(TravelMateMatch::getDestination, Collectors.counting()));

            // 按旅行风格分组
            Map<String, Long> travelStyleCounts = matches.stream()
                    .collect(Collectors.groupingBy(TravelMateMatch::getTravelStyle, Collectors.counting()));

            analysis.put("averageMatchScore", averageScore);
            analysis.put("totalMatches", matches.size());
            analysis.put("destinationDistribution", destinationCounts);
            analysis.put("travelStyleDistribution", travelStyleCounts);
            analysis.put("topMatches", matches.stream()
                    .sorted((m1, m2) -> Integer.compare(m2.getMatchScore(), m1.getMatchScore()))
                    .limit(3)
                    .collect(Collectors.toList()));
        } else {
            analysis.put("message", "No matches found yet");
        }

        return analysis;
    }
}