package com.travel.destination.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.destination.entity.Destination;
import com.travel.destination.entity.DestinationImage;
import com.travel.destination.entity.DestinationTag;
import com.travel.destination.entity.Attraction;

import java.util.List;
import java.util.Map;

/**
 * 目的地服务接口
 */
public interface DestinationService {

    /**
     * 分页获取目的地列表
     * @param query 分页查询参数
     * @return 分页结果
     */
    PageResult<Destination> getDestinations(PageQuery query);

    /**
     * 根据ID获取目的地详情
     * @param id 目的地ID
     * @return 目的地详情
     */
    Destination getDestinationById(Long id);

    /**
     * 创建目的地
     * @param destination 目的地信息
     * @return 创建的目的地
     */
    Destination createDestination(Destination destination);

    /**
     * 更新目的地
     * @param id 目的地ID
     * @param destination 目的地信息
     * @return 更新后的目的地
     */
    Destination updateDestination(Long id, Destination destination);

    /**
     * 删除目的地
     * @param id 目的地ID
     * @return 是否删除成功
     */
    boolean deleteDestination(Long id);

    /**
     * 获取目的地的景点列表
     * @param destinationId 目的地ID
     * @return 景点列表
     */
    List<Attraction> getAttractionsByDestinationId(Long destinationId);

    /**
     * 获取热门目的地
     * @param limit 限制数量
     * @return 热门目的地列表
     */
    List<Destination> getPopularDestinations(Integer limit);

    /**
     * 搜索目的地
     * @param keyword 关键词
     * @return 搜索结果
     */
    List<Destination> searchDestinations(String keyword);

    /**
     * 根据国家获取目的地
     * @param country 国家
     * @return 目的地列表
     */
    List<Destination> getDestinationsByCountry(String country);

    /**
     * 根据城市获取目的地
     * @param city 城市
     * @return 目的地列表
     */
    List<Destination> getDestinationsByCity(String city);

    /**
     * 获取目的地图片列表
     * @param destinationId 目的地ID
     * @return 图片列表
     */
    List<DestinationImage> getDestinationImages(Long destinationId);

    /**
     * 添加目的地图片
     * @param destinationId 目的地ID
     * @param image 图片信息
     * @return 添加的图片
     */
    DestinationImage addDestinationImage(Long destinationId, DestinationImage image);

    /**
     * 删除目的地图片
     * @param imageId 图片ID
     * @return 是否删除成功
     */
    boolean deleteDestinationImage(Long imageId);

    /**
     * 获取目的地标签列表
     * @param destinationId 目的地ID
     * @return 标签列表
     */
    List<DestinationTag> getDestinationTags(Long destinationId);

    /**
     * 添加目的地标签
     * @param destinationId 目的地ID
     * @param tag 标签信息
     * @return 添加的标签
     */
    DestinationTag addDestinationTag(Long destinationId, DestinationTag tag);

    /**
     * 删除目的地标签
     * @param tagId 标签ID
     * @return 是否删除成功
     */
    boolean deleteDestinationTag(Long tagId);

    /**
     * 根据标签获取目的地
     * @param tagName 标签名称
     * @param limit 限制数量
     * @return 目的地列表
     */
    List<Destination> getDestinationsByTag(String tagName, Integer limit);

    /**
     * 获取目的地统计信息
     * @return 统计信息
     */
    Map<String, Object> getDestinationStatistics();

    /**
     * 推荐目的地
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 推荐的目的地列表
     */
    List<Destination> recommendDestinations(Long userId, Integer limit);
}
