package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.destination.entity.AttractionReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景点评价数据访问接口
 */
public interface AttractionReviewDao extends BaseMapper<AttractionReview> {

    /**
     * 分页查询景点评价
     * @param page 分页对象
     * @param attractionId 景点ID
     * @param status 状态
     * @return 分页结果
     */
    IPage<AttractionReview> selectPageByAttractionId(
            IPage<AttractionReview> page,
            @Param("attractionId") Long attractionId,
            @Param("status") Integer status
    );

    /**
     * 根据景点ID获取评价列表
     * @param attractionId 景点ID
     * @param status 状态
     * @param limit 限制数量
     * @return 评价列表
     */
    List<AttractionReview> selectByAttractionId(
            @Param("attractionId") Long attractionId,
            @Param("status") Integer status,
            @Param("limit") Integer limit
    );

    /**
     * 根据用户ID获取评价列表
     * @param userId 用户ID
     * @param status 状态
     * @param limit 限制数量
     * @return 评价列表
     */
    List<AttractionReview> selectByUserId(
            @Param("userId") Long userId,
            @Param("status") Integer status,
            @Param("limit") Integer limit
    );

    /**
     * 获取景点的平均评分
     * @param attractionId 景点ID
     * @return 平均评分
     */
    Double selectAverageRatingByAttractionId(@Param("attractionId") Long attractionId);

    /**
     * 检查用户是否已经评价过该景点
     * @param attractionId 景点ID
     * @param userId 用户ID
     * @return 是否评价过
     */
    Integer countByAttractionIdAndUserId(
            @Param("attractionId") Long attractionId,
            @Param("userId") Long userId
    );

    /**
     * 获取景点的评价数量
     * @param attractionId 景点ID
     * @return 评价数量
     */
    Integer countByAttractionId(@Param("attractionId") Long attractionId);
}
