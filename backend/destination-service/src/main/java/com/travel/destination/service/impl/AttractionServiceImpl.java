package com.travel.destination.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.destination.dao.AttractionDao;
import com.travel.destination.dao.AttractionImageDao;
import com.travel.destination.dao.AttractionTagDao;
import com.travel.destination.dao.AttractionReviewDao;
import com.travel.destination.entity.Attraction;
import com.travel.destination.entity.AttractionImage;
import com.travel.destination.entity.AttractionTag;
import com.travel.destination.entity.AttractionReview;
import com.travel.destination.service.AttractionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 景点服务实现类
 */
@Slf4j
@Service
public class AttractionServiceImpl implements AttractionService {

    @Resource
    private AttractionDao attractionDao;

    @Resource
    private AttractionImageDao attractionImageDao;

    @Resource
    private AttractionTagDao attractionTagDao;

    @Resource
    private AttractionReviewDao attractionReviewDao;

    @Override
    public PageResult<Attraction> getAttractions(PageQuery query) {
        // 构建分页参数
        IPage<Attraction> page = new Page<>(query.getPage(), query.getSize());

        // 执行分页查询（暂时不处理复杂参数，只做简单分页）
        IPage<Attraction> result = attractionDao.selectPage(page, null);

        // 构建返回结果
        return new PageResult<Attraction>(
                result.getTotal(),
                query.getSize(),
                (int) result.getPages(),
                query.getPage(),
                result.getRecords()
        );
    }

    @Override
    public Attraction getAttractionById(Long id) {
        return attractionDao.selectById(id);
    }

    @Override
    @Transactional
    public Attraction createAttraction(Attraction attraction) {
        // 检查景点名称是否存在
        Integer count = attractionDao.countByNameAndDestinationId(
                attraction.getName(),
                attraction.getDestinationId(),
                null
        );
        if (count > 0) {
            throw new RuntimeException("景点名称已存在");
        }

        // 设置默认值
        attraction.setStatus(1);
        attraction.setDeleted(0);
        attraction.setRating(0.0);
        attraction.setCreatedAt(LocalDateTime.now());
        attraction.setUpdatedAt(LocalDateTime.now());

        // 保存景点
        attractionDao.insert(attraction);
        log.info("创建景点成功: {}", attraction.getName());
        return attraction;
    }

    @Override
    @Transactional
    public Attraction updateAttraction(Long id, Attraction attraction) {
        // 检查景点是否存在
        Attraction existing = attractionDao.selectById(id);
        if (existing == null) {
            throw new RuntimeException("景点不存在");
        }

        // 检查景点名称是否重复
        Integer count = attractionDao.countByNameAndDestinationId(
                attraction.getName(),
                attraction.getDestinationId(),
                id
        );
        if (count > 0) {
            throw new RuntimeException("景点名称已存在");
        }

        // 更新字段
        existing.setName(attraction.getName());
        existing.setDescription(attraction.getDescription());
        existing.setAddress(attraction.getAddress());
        existing.setImageUrl(attraction.getImageUrl());
        existing.setLatitude(attraction.getLatitude());
        existing.setLongitude(attraction.getLongitude());
        existing.setCategory(attraction.getCategory());
        existing.setOpeningHours(attraction.getOpeningHours());
        existing.setTicketPrice(attraction.getTicketPrice());
        existing.setStatus(attraction.getStatus());
        existing.setUpdatedBy(attraction.getUpdatedBy());
        existing.setUpdatedAt(LocalDateTime.now());

        // 保存更新
        attractionDao.updateById(existing);
        log.info("更新景点成功: {}", existing.getName());
        return existing;
    }

    @Override
    @Transactional
    public boolean deleteAttraction(Long id) {
        // 检查景点是否存在
        Attraction existing = attractionDao.selectById(id);
        if (existing == null) {
            throw new RuntimeException("景点不存在");
        }

        // 删除景点（逻辑删除）
        existing.setDeleted(1);
        existing.setUpdatedAt(LocalDateTime.now());
        int result = attractionDao.updateById(existing);
        log.info("删除景点成功: {}", existing.getName());
        return result > 0;
    }

    @Override
    public List<AttractionReview> getReviewsByAttractionId(Long attractionId) {
        return attractionReviewDao.selectByAttractionId(attractionId, 1, 20);
    }

    @Override
    public List<Attraction> getPopularAttractions(Integer limit) {
        return attractionDao.selectPopularAttractions(limit);
    }

    @Override
    public List<Attraction> searchAttractions(String keyword) {
        return attractionDao.searchAttractions(keyword, 20);
    }

    @Override
    public List<Attraction> getAttractionsByCategory(String category, Integer limit) {
        return attractionDao.selectByCategory(category, limit);
    }

    @Override
    public List<AttractionImage> getAttractionImages(Long attractionId) {
        return attractionImageDao.selectByAttractionId(attractionId, 1);
    }

    @Override
    @Transactional
    public AttractionImage addAttractionImage(Long attractionId, AttractionImage image) {
        // 检查景点是否存在
        Attraction attraction = attractionDao.selectById(attractionId);
        if (attraction == null) {
            throw new RuntimeException("景点不存在");
        }

        // 设置景点ID
        image.setAttractionId(attractionId);
        image.setStatus(1);
        image.setDeleted(0);
        image.setCreatedAt(LocalDateTime.now());
        image.setUpdatedAt(LocalDateTime.now());

        // 计算排序值
        Integer maxSort = attractionImageDao.countByAttractionId(attractionId);
        image.setSort(maxSort != null ? maxSort + 1 : 1);

        // 保存图片
        attractionImageDao.insert(image);
        log.info("添加景点图片成功: {}", image.getImageUrl());
        return image;
    }

    @Override
    @Transactional
    public boolean deleteAttractionImage(Long imageId) {
        // 检查图片是否存在
        AttractionImage image = attractionImageDao.selectById(imageId);
        if (image == null) {
            throw new RuntimeException("图片不存在");
        }

        // 删除图片（逻辑删除）
        image.setDeleted(1);
        image.setUpdatedAt(LocalDateTime.now());
        int result = attractionImageDao.updateById(image);
        log.info("删除景点图片成功: {}", image.getImageUrl());
        return result > 0;
    }

    @Override
    public List<AttractionTag> getAttractionTags(Long attractionId) {
        return attractionTagDao.selectByAttractionId(attractionId, 1);
    }

    @Override
    @Transactional
    public AttractionTag addAttractionTag(Long attractionId, AttractionTag tag) {
        // 检查景点是否存在
        Attraction attraction = attractionDao.selectById(attractionId);
        if (attraction == null) {
            throw new RuntimeException("景点不存在");
        }

        // 检查标签是否已存在
        Integer count = attractionTagDao.countByAttractionIdAndTagName(attractionId, tag.getTagName());
        if (count > 0) {
            throw new RuntimeException("标签已存在");
        }

        // 设置景点ID
        tag.setAttractionId(attractionId);
        tag.setStatus(1);
        tag.setDeleted(0);
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());

        // 保存标签
        attractionTagDao.insert(tag);
        log.info("添加景点标签成功: {}", tag.getTagName());
        return tag;
    }

    @Override
    @Transactional
    public boolean deleteAttractionTag(Long tagId) {
        // 检查标签是否存在
        AttractionTag tag = attractionTagDao.selectById(tagId);
        if (tag == null) {
            throw new RuntimeException("标签不存在");
        }

        // 删除标签（逻辑删除）
        tag.setDeleted(1);
        tag.setUpdatedAt(LocalDateTime.now());
        int result = attractionTagDao.updateById(tag);
        log.info("删除景点标签成功: {}", tag.getTagName());
        return result > 0;
    }

    @Override
    @Transactional
    public AttractionReview addAttractionReview(AttractionReview review) {
        // 检查景点是否存在
        Attraction attraction = attractionDao.selectById(review.getAttractionId());
        if (attraction == null) {
            throw new RuntimeException("景点不存在");
        }

        // 检查用户是否已经评价过
        Integer count = attractionReviewDao.countByAttractionIdAndUserId(review.getAttractionId(), review.getUserId());
        if (count > 0) {
            throw new RuntimeException("您已经评价过该景点");
        }

        // 设置默认值
        review.setStatus(1);
        review.setDeleted(0);
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        // 保存评价
        attractionReviewDao.insert(review);
        log.info("添加景点评价成功: 用户ID={}, 景点ID={}", review.getUserId(), review.getAttractionId());

        // 更新景点评分
        updateAttractionRating(review.getAttractionId());

        return review;
    }

    @Override
    @Transactional
    public AttractionReview updateAttractionReview(Long id, AttractionReview review) {
        // 检查评价是否存在
        AttractionReview existing = attractionReviewDao.selectById(id);
        if (existing == null) {
            throw new RuntimeException("评价不存在");
        }

        // 更新字段
        existing.setRating(review.getRating());
        existing.setContent(review.getContent());
        existing.setStatus(review.getStatus());
        existing.setUpdatedBy(review.getUpdatedBy());
        existing.setUpdatedAt(LocalDateTime.now());

        // 保存更新
        attractionReviewDao.updateById(existing);
        log.info("更新景点评价成功: 评价ID={}", id);

        // 更新景点评分
        updateAttractionRating(existing.getAttractionId());

        return existing;
    }

    @Override
    @Transactional
    public boolean deleteAttractionReview(Long id) {
        // 检查评价是否存在
        AttractionReview review = attractionReviewDao.selectById(id);
        if (review == null) {
            throw new RuntimeException("评价不存在");
        }

        // 记录景点ID
        Long attractionId = review.getAttractionId();

        // 删除评价（逻辑删除）
        review.setDeleted(1);
        review.setUpdatedAt(LocalDateTime.now());
        int result = attractionReviewDao.updateById(review);
        log.info("删除景点评价成功: 评价ID={}", id);

        // 更新景点评分
        updateAttractionRating(attractionId);

        return result > 0;
    }

    @Override
    public List<Attraction> getAttractionsByTag(String tagName, Integer limit) {
        // 根据标签名称获取景点ID列表
        List<Long> attractionIds = attractionTagDao.selectAttractionIdsByTagName(tagName, limit);
        if (attractionIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 根据ID列表获取景点列表
        return attractionDao.selectBatchIds(attractionIds);
    }

    @Override
    public Map<String, Object> getAttractionStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        // 这里应该添加各种统计信息，暂时返回空统计
        statistics.put("totalAttractions", 0);
        statistics.put("totalCategories", 0);
        statistics.put("totalReviews", 0);
        statistics.put("averageRating", 0.0);
        statistics.put("popularAttractions", new ArrayList<>());

        return statistics;
    }

    @Override
    public List<Attraction> recommendAttractions(Long userId, Integer limit) {
        // 这里应该实现推荐算法，暂时返回热门景点
        return attractionDao.selectPopularAttractions(limit);
    }

    /**
     * 更新景点评分
     * @param attractionId 景点ID
     */
    private void updateAttractionRating(Long attractionId) {
        // 获取平均评分
        Double averageRating = attractionReviewDao.selectAverageRatingByAttractionId(attractionId);

        // 更新景点评分
        Attraction attraction = attractionDao.selectById(attractionId);
        if (attraction != null) {
            attraction.setRating(averageRating != null ? averageRating : 0.0);
            attraction.setUpdatedAt(LocalDateTime.now());
            attractionDao.updateById(attraction);
        }
    }
}
