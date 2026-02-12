package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.destination.entity.Attraction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景点数据访问接口
 */
public interface AttractionDao extends BaseMapper<Attraction> {

    /**
     * 分页查询景点
     * @param page 分页对象
     * @param destinationId 目的地ID
     * @param keyword 关键词
     * @param category 分类
     * @param status 状态
     * @return 分页结果
     */
    IPage<Attraction> selectPageWithConditions(
            IPage<Attraction> page,
            @Param("destinationId") Long destinationId,
            @Param("keyword") String keyword,
            @Param("category") String category,
            @Param("status") Integer status
    );

    /**
     * 根据目的地ID获取景点列表
     * @param destinationId 目的地ID
     * @param status 状态
     * @return 景点列表
     */
    List<Attraction> selectByDestinationId(
            @Param("destinationId") Long destinationId,
            @Param("status") Integer status
    );

    /**
     * 获取热门景点
     * @param limit 限制数量
     * @return 热门景点列表
     */
    List<Attraction> selectPopularAttractions(@Param("limit") Integer limit);

    /**
     * 搜索景点
     * @param keyword 关键词
     * @param limit 限制数量
     * @return 搜索结果
     */
    List<Attraction> searchAttractions(@Param("keyword") String keyword, @Param("limit") Integer limit);

    /**
     * 根据分类获取景点
     * @param category 分类
     * @param limit 限制数量
     * @return 景点列表
     */
    List<Attraction> selectByCategory(@Param("category") String category, @Param("limit") Integer limit);

    /**
     * 检查景点名称是否存在
     * @param name 景点名称
     * @param destinationId 目的地ID
     * @param excludeId 排除的ID
     * @return 是否存在
     */
    Integer countByNameAndDestinationId(
            @Param("name") String name,
            @Param("destinationId") Long destinationId,
            @Param("excludeId") Long excludeId
    );
}
