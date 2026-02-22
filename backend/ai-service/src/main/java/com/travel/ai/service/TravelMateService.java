package com.travel.ai.service;

import com.travel.ai.entity.RecruitPost;
import com.travel.ai.entity.TravelMateMatch;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface TravelMateService {
    /**
     * 匹配旅伴
     * @param userId 用户ID
     * @param destination 目的地
     * @param travelDate 旅行日期
     * @param travelStyle 旅行风格
     * @param interests 兴趣爱好
     * @return 匹配结果列表
     */
    List<TravelMateMatch> matchTravelMates(Long userId, String destination, String travelDate, String travelStyle, String interests);

    /**
     * 获取用户的旅伴匹配列表
     * @param userId 用户ID
     * @param query 分页查询参数
     * @return 匹配列表
     */
    PageResult<TravelMateMatch> getUserMatches(Long userId, PageQuery query);

    /**
     * 创建招募帖
     * @param recruitPost 招募帖信息
     * @return 创建的招募帖
     */
    RecruitPost createRecruitPost(RecruitPost recruitPost);

    /**
     * 获取招募帖列表
     * @param query 分页查询参数
     * @return 招募帖列表
     */
    PageResult<RecruitPost> getRecruitPosts(PageQuery query);

    /**
     * 获取招募帖详情
     * @param id 招募帖ID
     * @return 招募帖详情
     */
    RecruitPost getRecruitPostById(Long id);

    /**
     * 更新招募帖
     * @param recruitPost 招募帖信息
     * @return 更新后的招募帖
     */
    RecruitPost updateRecruitPost(RecruitPost recruitPost);

    /**
     * 删除招募帖
     * @param id 招募帖ID
     */
    void deleteRecruitPost(Long id);

    /**
     * 搜索招募帖
     * @param keyword 关键词
     * @param query 分页查询参数
     * @return 匹配的招募帖列表
     */
    PageResult<RecruitPost> searchRecruitPosts(String keyword, PageQuery query);

    /**
     * 获取AI匹配分析
     * @param userId 用户ID
     * @return 匹配分析结果
     */
    Map<String, Object> getMatchAnalysis(Long userId);
}