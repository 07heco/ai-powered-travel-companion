package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.destination.entity.AttractionImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景点图片数据访问接口
 */
public interface AttractionImageDao extends BaseMapper<AttractionImage> {

    /**
     * 根据景点ID获取图片列表
     * @param attractionId 景点ID
     * @param status 状态
     * @return 图片列表
     */
    List<AttractionImage> selectByAttractionId(
            @Param("attractionId") Long attractionId,
            @Param("status") Integer status
    );

    /**
     * 根据景点ID删除图片
     * @param attractionId 景点ID
     * @return 影响行数
     */
    Integer deleteByAttractionId(@Param("attractionId") Long attractionId);

    /**
     * 获取景点图片数量
     * @param attractionId 景点ID
     * @return 图片数量
     */
    Integer countByAttractionId(@Param("attractionId") Long attractionId);
}
