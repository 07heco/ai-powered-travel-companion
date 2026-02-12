package com.travel.destination.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.destination.entity.Attraction;
import com.travel.destination.entity.AttractionImage;
import com.travel.destination.entity.AttractionTag;
import com.travel.destination.entity.AttractionReview;

import java.util.List;
import java.util.Map;

/**
 * 景点服务接口
 */
public interface AttractionService {

    /**
     * 分页获取景点列表
     * @param query 分页查询参数
     * @return 分页结果
     */
    PageResult<Attraction> getAttractions(PageQuery query);

    /**
     * 根据ID获取景点详情
     * @param id 景点ID
     * @return 景点详情
     */
    Attraction getAttractionById(Long id);

    /**
     * 创建景点
     * @param attraction 景点信息
     * @return 创建的景点
     */
    Attraction createAttraction(Attraction attraction);

    /**
     * 更新景点
     * @param id 景点ID
     * @param attraction 景点信息
     * @return 更新后的景点
     */
    Attraction updateAttraction(Long id, Attraction attraction);

    /**
     * 删除景点
     * @param id 景点ID
     * @return 是否删除成功
     */
    boolean deleteAttraction(Long id);

    /**
     * 获取景点的评价列表
     * @param attractionId 景点ID
     * @return 评价列表
     */
    List<AttractionReview> getReviewsByAttractionId(Long attractionId);

    /**
     * 获取热门景点
     * @param limit 限制数量
     * @return 热门景点列表
     */
    List<Attraction> getPopularAttractions(Integer limit);

    /**
     * 搜索景点
     * @param keyword 关键词
     * @return 搜索结果
     */
    List<Attraction> searchAttractions(String keyword);

    /**
     * 根据分类获取景点
     * @param category 分类
     * @param limit 限制数量
     * @return 景点列表
     */
    List<Attraction> getAttractionsByCategory(String category, Integer limit);

    /**
     * 获取景点图片列表
     * @param attractionId 景点ID
     * @return 图片列表
     */
    List<AttractionImage> getAttractionImages(Long attractionId);

    /**
     * 添加景点图片
     * @param attractionId 景点ID
     * @param image 图片信息
     * @return 添加的图片
     */
    AttractionImage addAttractionImage(Long attractionId, AttractionImage image);

    /**
     * 删除景点图片
     * @param imageId 图片ID
     * @return 是否删除成功
     */
    boolean deleteAttractionImage(Long imageId);

    /**
     * 获取景点标签列表
     * @param attractionId 景点ID
     * @return 标签列表
     */
    List<AttractionTag> getAttractionTags(Long attractionId);

    /**
     * 添加景点标签
     * @param attractionId 景点ID
     * @param tag 标签信息
     * @return 添加的标签
     */
    AttractionTag addAttractionTag(Long attractionId, AttractionTag tag);

    /**
     * 删除景点标签
     * @param tagId 标签ID
     * @return 是否删除成功
     */
    boolean deleteAttractionTag(Long tagId);

    /**
     * 添加景点评价
     * @param review 评价信息
     * @return 添加的评价
     */
    AttractionReview addAttractionReview(AttractionReview review);

    /**
     * 更新景点评价
     * @param id 评价ID
     * @param review 评价信息
     * @return 更新后的评价
     */
    AttractionReview updateAttractionReview(Long id, AttractionReview review);

    /**
     * 删除景点评价
     * @param id 评价ID
     * @return 是否删除成功
     */
    boolean deleteAttractionReview(Long id);

    /**
     * 根据标签获取景点
     * @param tagName 标签名称
     * @param limit 限制数量
     * @return 景点列表
     */
    List<Attraction> getAttractionsByTag(String tagName, Integer limit);

    /**
     * 获取景点统计信息
     * @return 统计信息
     */
    Map<String, Object> getAttractionStatistics();

    /**
     * 推荐景点
     * @param userId 用户ID
     * @param limit 限制数量
     * @return 推荐的景点列表
     */
    List<Attraction> recommendAttractions(Long userId, Integer limit);
}
