package com.travel.ai.service;

import com.travel.ai.entity.ARLandmark;

import java.util.List;

public interface ARService {
    /**
     * 识别景点
     * @param imageUrl 图片URL
     * @return 识别到的景点信息
     */
    ARLandmark recognizeLandmark(String imageUrl);

    /**
     * 根据ID获取景点详情
     * @param id 景点ID
     * @return 景点详情
     */
    ARLandmark getLandmarkById(Long id);

    /**
     * 根据位置获取附近景点
     * @param latitude 纬度
     * @param longitude 经度
     * @param radius 半径（米）
     * @return 附近景点列表
     */
    List<ARLandmark> getNearbyLandmarks(Double latitude, Double longitude, Integer radius);

    /**
     * 搜索景点
     * @param keyword 关键词
     * @return 匹配的景点列表
     */
    List<ARLandmark> searchLandmarks(String keyword);
}