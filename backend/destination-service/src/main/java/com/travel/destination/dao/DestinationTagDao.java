package com.travel.destination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.destination.entity.DestinationTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 目的地标签数据访问接口
 */
public interface DestinationTagDao extends BaseMapper<DestinationTag> {

    /**
     * 根据目的地ID获取标签列表
     * @param destinationId 目的地ID
     * @param status 状态
     * @return 标签列表
     */
    List<DestinationTag> selectByDestinationId(
            @Param("destinationId") Long destinationId,
            @Param("status") Integer status
    );

    /**
     * 根据目的地ID删除标签
     * @param destinationId 目的地ID
     * @return 影响行数
     */
    Integer deleteByDestinationId(@Param("destinationId") Long destinationId);

    /**
     * 检查标签是否存在
     * @param destinationId 目的地ID
     * @param tagName 标签名称
     * @return 是否存在
     */
    Integer countByDestinationIdAndTagName(
            @Param("destinationId") Long destinationId,
            @Param("tagName") String tagName
    );

    /**
     * 根据标签名称获取目的地
     * @param tagName 标签名称
     * @param limit 限制数量
     * @return 目的地ID列表
     */
    List<Long> selectDestinationIdsByTagName(
            @Param("tagName") String tagName,
            @Param("limit") Integer limit
    );
}
