package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.destination.entity.DestinationImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 目的地图片数据访问接口
 */
public interface DestinationImageDao extends BaseMapper<DestinationImage> {

    /**
     * 根据目的地ID获取图片列表
     * @param destinationId 目的地ID
     * @param status 状态
     * @return 图片列表
     */
    List<DestinationImage> selectByDestinationId(
            @Param("destinationId") Long destinationId,
            @Param("status") Integer status
    );

    /**
     * 根据目的地ID删除图片
     * @param destinationId 目的地ID
     * @return 影响行数
     */
    Integer deleteByDestinationId(@Param("destinationId") Long destinationId);

    /**
     * 获取目的地图片数量
     * @param destinationId 目的地ID
     * @return 图片数量
     */
    Integer countByDestinationId(@Param("destinationId") Long destinationId);
}
