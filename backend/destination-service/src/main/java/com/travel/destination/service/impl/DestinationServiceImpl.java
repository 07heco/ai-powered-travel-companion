package com.travel.destination.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.destination.dao.DestinationDao;
import com.travel.destination.dao.DestinationImageDao;
import com.travel.destination.dao.DestinationTagDao;
import com.travel.destination.entity.Destination;
import com.travel.destination.entity.DestinationImage;
import com.travel.destination.entity.DestinationTag;
import com.travel.destination.entity.Attraction;
import com.travel.destination.service.DestinationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 目的地服务实现类
 */
@Slf4j
@Service
public class DestinationServiceImpl implements DestinationService {

    @Resource
    private DestinationDao destinationDao;

    @Resource
    private DestinationImageDao destinationImageDao;

    @Resource
    private DestinationTagDao destinationTagDao;

    @Override
    public PageResult<Destination> getDestinations(PageQuery query) {
        // 构建分页参数
        IPage<Destination> page = new Page<>(query.getPage(), query.getSize());

        // 执行分页查询（暂时不处理复杂参数，只做简单分页）
        IPage<Destination> result = destinationDao.selectPage(page, null);

        // 构建返回结果
        return new PageResult<Destination>(
                result.getTotal(),
                query.getSize(),
                (int) result.getPages(),
                query.getPage(),
                result.getRecords()
        );
    }

    @Override
    public Destination getDestinationById(Long id) {
        return destinationDao.selectById(id);
    }

    @Override
    @Transactional
    public Destination createDestination(Destination destination) {
        // 检查目的地名称是否存在
        Integer count = destinationDao.countByNameAndCity(destination.getName(), destination.getCity(), null);
        if (count > 0) {
            throw new RuntimeException("目的地名称已存在");
        }

        // 设置默认值
        destination.setStatus(1);
        destination.setDeleted(0);
        destination.setPopularScore(0);
        destination.setCreatedAt(LocalDateTime.now());
        destination.setUpdatedAt(LocalDateTime.now());

        // 保存目的地
        destinationDao.insert(destination);
        log.info("创建目的地成功: {}", destination.getName());
        return destination;
    }

    @Override
    @Transactional
    public Destination updateDestination(Long id, Destination destination) {
        // 检查目的地是否存在
        Destination existing = destinationDao.selectById(id);
        if (existing == null) {
            throw new RuntimeException("目的地不存在");
        }

        // 检查目的地名称是否重复
        Integer count = destinationDao.countByNameAndCity(destination.getName(), destination.getCity(), id);
        if (count > 0) {
            throw new RuntimeException("目的地名称已存在");
        }

        // 更新字段
        existing.setName(destination.getName());
        existing.setCountry(destination.getCountry());
        existing.setCity(destination.getCity());
        existing.setDescription(destination.getDescription());
        existing.setCoverImage(destination.getCoverImage());
        existing.setLatitude(destination.getLatitude());
        existing.setLongitude(destination.getLongitude());
        existing.setPopularScore(destination.getPopularScore());
        existing.setStatus(destination.getStatus());
        existing.setUpdatedBy(destination.getUpdatedBy());
        existing.setUpdatedAt(LocalDateTime.now());

        // 保存更新
        destinationDao.updateById(existing);
        log.info("更新目的地成功: {}", existing.getName());
        return existing;
    }

    @Override
    @Transactional
    public boolean deleteDestination(Long id) {
        // 检查目的地是否存在
        Destination existing = destinationDao.selectById(id);
        if (existing == null) {
            throw new RuntimeException("目的地不存在");
        }

        // 删除目的地（逻辑删除）
        existing.setDeleted(1);
        existing.setUpdatedAt(LocalDateTime.now());
        int result = destinationDao.updateById(existing);
        log.info("删除目的地成功: {}", existing.getName());
        return result > 0;
    }

    @Override
    public List<Attraction> getAttractionsByDestinationId(Long destinationId) {
        // 这里应该调用 AttractionService 的方法，暂时返回空列表
        return new ArrayList<>();
    }

    @Override
    public List<Destination> getPopularDestinations(Integer limit) {
        return destinationDao.selectPopularDestinations(limit);
    }

    @Override
    public List<Destination> searchDestinations(String keyword) {
        return destinationDao.searchDestinations(keyword, 20);
    }

    @Override
    public List<Destination> getDestinationsByCountry(String country) {
        return destinationDao.selectByCountry(country);
    }

    @Override
    public List<Destination> getDestinationsByCity(String city) {
        return destinationDao.selectByCity(city);
    }

    @Override
    public List<DestinationImage> getDestinationImages(Long destinationId) {
        return destinationImageDao.selectByDestinationId(destinationId, 1);
    }

    @Override
    @Transactional
    public DestinationImage addDestinationImage(Long destinationId, DestinationImage image) {
        // 检查目的地是否存在
        Destination destination = destinationDao.selectById(destinationId);
        if (destination == null) {
            throw new RuntimeException("目的地不存在");
        }

        // 设置目的地ID
        image.setDestinationId(destinationId);
        image.setStatus(1);
        image.setDeleted(0);
        image.setCreatedAt(LocalDateTime.now());
        image.setUpdatedAt(LocalDateTime.now());

        // 计算排序值
        Integer maxSort = destinationImageDao.countByDestinationId(destinationId);
        image.setSort(maxSort != null ? maxSort + 1 : 1);

        // 保存图片
        destinationImageDao.insert(image);
        log.info("添加目的地图片成功: {}", image.getImageUrl());
        return image;
    }

    @Override
    @Transactional
    public boolean deleteDestinationImage(Long imageId) {
        // 检查图片是否存在
        DestinationImage image = destinationImageDao.selectById(imageId);
        if (image == null) {
            throw new RuntimeException("图片不存在");
        }

        // 删除图片（逻辑删除）
        image.setDeleted(1);
        image.setUpdatedAt(LocalDateTime.now());
        int result = destinationImageDao.updateById(image);
        log.info("删除目的地图片成功: {}", image.getImageUrl());
        return result > 0;
    }

    @Override
    public List<DestinationTag> getDestinationTags(Long destinationId) {
        return destinationTagDao.selectByDestinationId(destinationId, 1);
    }

    @Override
    @Transactional
    public DestinationTag addDestinationTag(Long destinationId, DestinationTag tag) {
        // 检查目的地是否存在
        Destination destination = destinationDao.selectById(destinationId);
        if (destination == null) {
            throw new RuntimeException("目的地不存在");
        }

        // 检查标签是否已存在
        Integer count = destinationTagDao.countByDestinationIdAndTagName(destinationId, tag.getTagName());
        if (count > 0) {
            throw new RuntimeException("标签已存在");
        }

        // 设置目的地ID
        tag.setDestinationId(destinationId);
        tag.setStatus(1);
        tag.setDeleted(0);
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());

        // 保存标签
        destinationTagDao.insert(tag);
        log.info("添加目的地标签成功: {}", tag.getTagName());
        return tag;
    }

    @Override
    @Transactional
    public boolean deleteDestinationTag(Long tagId) {
        // 检查标签是否存在
        DestinationTag tag = destinationTagDao.selectById(tagId);
        if (tag == null) {
            throw new RuntimeException("标签不存在");
        }

        // 删除标签（逻辑删除）
        tag.setDeleted(1);
        tag.setUpdatedAt(LocalDateTime.now());
        int result = destinationTagDao.updateById(tag);
        log.info("删除目的地标签成功: {}", tag.getTagName());
        return result > 0;
    }

    @Override
    public List<Destination> getDestinationsByTag(String tagName, Integer limit) {
        // 根据标签名称获取目的地ID列表
        List<Long> destinationIds = destinationTagDao.selectDestinationIdsByTagName(tagName, limit);
        if (destinationIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 根据ID列表获取目的地列表
        return destinationDao.selectBatchIds(destinationIds);
    }

    @Override
    public Map<String, Object> getDestinationStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        // 这里应该添加各种统计信息，暂时返回空统计
        statistics.put("totalDestinations", 0);
        statistics.put("totalCountries", 0);
        statistics.put("totalCities", 0);
        statistics.put("popularDestinations", new ArrayList<>());

        return statistics;
    }

    @Override
    public List<Destination> recommendDestinations(Long userId, Integer limit) {
        // 这里应该实现推荐算法，暂时返回热门目的地
        return destinationDao.selectPopularDestinations(limit);
    }
}
