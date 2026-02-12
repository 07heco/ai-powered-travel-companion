package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.travel.destination.entity.Destination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 目的地数据访问接口
 */
public interface DestinationDao extends BaseMapper<Destination> {

    /**
     * 分页查询目的地
     * @param page 分页对象
     * @param keyword 关键词
     * @param country 国家
     * @param city 城市
     * @param status 状态
     * @return 分页结果
     */
    IPage<Destination> selectPageWithConditions(
            IPage<Destination> page,
            @Param("keyword") String keyword,
            @Param("country") String country,
            @Param("city") String city,
            @Param("status") Integer status
    );

    /**
     * 获取热门目的地
     * @param limit 限制数量
     * @return 热门目的地列表
     */
    List<Destination> selectPopularDestinations(@Param("limit") Integer limit);

    /**
     * 搜索目的地
     * @param keyword 关键词
     * @param limit 限制数量
     * @return 搜索结果
     */
    List<Destination> searchDestinations(@Param("keyword") String keyword, @Param("limit") Integer limit);

    /**
     * 根据国家获取目的地
     * @param country 国家
     * @return 目的地列表
     */
    List<Destination> selectByCountry(@Param("country") String country);

    /**
     * 根据城市获取目的地
     * @param city 城市
     * @return 目的地列表
     */
    List<Destination> selectByCity(@Param("city") String city);

    /**
     * 检查目的地名称是否存在
     * @param name 目的地名称
     * @param city 城市
     * @param excludeId 排除的ID
     * @return 是否存在
     */
    Integer countByNameAndCity(
            @Param("name") String name,
            @Param("city") String city,
            @Param("excludeId") Long excludeId
    );
}
