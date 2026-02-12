package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.destination.entity.AttractionTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景点标签数据访问接口
 */
public interface AttractionTagDao extends BaseMapper<AttractionTag> {

    /**
     * 根据景点ID获取标签列表
     * @param attractionId 景点ID
     * @param status 状态
     * @return 标签列表
     */
    List<AttractionTag> selectByAttractionId(
            @Param("attractionId") Long attractionId,
            @Param("status") Integer status
    );

    /**
     * 根据景点ID删除标签
     * @param attractionId 景点ID
     * @return 影响行数
     */
    Integer deleteByAttractionId(@Param("attractionId") Long attractionId);

    /**
     * 检查标签是否存在
     * @param attractionId 景点ID
     * @param tagName 标签名称
     * @return 是否存在
     */
    Integer countByAttractionIdAndTagName(
            @Param("attractionId") Long attractionId,
            @Param("tagName") String tagName
    );

    /**
     * 根据标签名称获取景点
     * @param tagName 标签名称
     * @param limit 限制数量
     * @return 景点ID列表
     */
    List<Long> selectAttractionIdsByTagName(
            @Param("tagName") String tagName,
            @Param("limit") Integer limit
    );
}
