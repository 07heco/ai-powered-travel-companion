package com.travel.destination.service.impl;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.destination.entity.Attraction;
import com.travel.destination.entity.AttractionImage;
import com.travel.destination.entity.AttractionReview;
import com.travel.destination.entity.AttractionTag;
import com.travel.destination.service.AttractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AttractionServiceImplTest {

    @Autowired
    private AttractionService attractionService;

    private Attraction testAttraction;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testAttraction = new Attraction();
        testAttraction.setName("测试景点");
        testAttraction.setDestinationId(1L);
        testAttraction.setDescription("测试景点描述");
        testAttraction.setAddress("测试地址");
        testAttraction.setCategory("文化古迹");
        testAttraction.setStatus(1);
    }

    @Test
    void testGetAttractions() {
        PageQuery query = new PageQuery();
        query.setPage(1);
        query.setSize(10);
        
        PageResult<Attraction> result = attractionService.getAttractions(query);
        assertNotNull(result);
        assertNotNull(result.getRecords());
        assertTrue(result.getTotal() >= 0);
    }

    @Test
    void testGetAttractionById() {
        // 测试获取已存在的景点
        Attraction attraction = attractionService.getAttractionById(1L);
        assertNotNull(attraction);
        assertEquals("故宫", attraction.getName());
    }

    @Test
    void testCreateAttraction() {
        Attraction created = attractionService.createAttraction(testAttraction);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("测试景点", created.getName());
    }

    @Test
    void testUpdateAttraction() {
        // 先创建一个景点
        Attraction created = attractionService.createAttraction(testAttraction);
        
        // 更新景点信息
        created.setName("更新后的景点名称");
        created.setDescription("更新后的景点描述");
        
        Attraction updated = attractionService.updateAttraction(created.getId(), created);
        assertNotNull(updated);
        assertEquals("更新后的景点名称", updated.getName());
        assertEquals("更新后的景点描述", updated.getDescription());
    }

    @Test
    void testDeleteAttraction() {
        // 先创建一个景点
        Attraction created = attractionService.createAttraction(testAttraction);
        
        // 删除景点
        boolean deleted = attractionService.deleteAttraction(created.getId());
        assertTrue(deleted);
    }

    @Test
    void testGetReviewsByAttractionId() {
        List<AttractionReview> reviews = attractionService.getReviewsByAttractionId(1L);
        assertNotNull(reviews);
    }

    @Test
    void testGetPopularAttractions() {
        List<Attraction> attractions = attractionService.getPopularAttractions(5);
        assertNotNull(attractions);
        assertTrue(attractions.size() <= 5);
    }

    @Test
    void testSearchAttractions() {
        List<Attraction> attractions = attractionService.searchAttractions("故宫");
        assertNotNull(attractions);
    }

    @Test
    void testGetAttractionsByCategory() {
        List<Attraction> attractions = attractionService.getAttractionsByCategory("文化古迹", 10);
        assertNotNull(attractions);
    }

    @Test
    void testGetAttractionImages() {
        List<AttractionImage> images = attractionService.getAttractionImages(1L);
        assertNotNull(images);
    }

    @Test
    void testAddAttractionImage() {
        AttractionImage image = new AttractionImage();
        image.setImageUrl("https://example.com/test.jpg");
        
        AttractionImage added = attractionService.addAttractionImage(1L, image);
        assertNotNull(added);
        assertNotNull(added.getId());
    }

    @Test
    void testDeleteAttractionImage() {
        // 先添加一个图片
        AttractionImage image = new AttractionImage();
        image.setImageUrl("https://example.com/test.jpg");
        AttractionImage added = attractionService.addAttractionImage(1L, image);
        
        // 删除图片
        boolean deleted = attractionService.deleteAttractionImage(added.getId());
        assertTrue(deleted);
    }

    @Test
    void testGetAttractionTags() {
        List<AttractionTag> tags = attractionService.getAttractionTags(1L);
        assertNotNull(tags);
    }

    @Test
    void testAddAttractionTag() {
        AttractionTag tag = new AttractionTag();
        tag.setTagName("测试标签");
        
        AttractionTag added = attractionService.addAttractionTag(1L, tag);
        assertNotNull(added);
        assertNotNull(added.getId());
    }

    @Test
    void testDeleteAttractionTag() {
        // 先添加一个标签
        AttractionTag tag = new AttractionTag();
        tag.setTagName("测试标签");
        AttractionTag added = attractionService.addAttractionTag(1L, tag);
        
        // 删除标签
        boolean deleted = attractionService.deleteAttractionTag(added.getId());
        assertTrue(deleted);
    }

    @Test
    void testAddAttractionReview() {
        AttractionReview review = new AttractionReview();
        review.setAttractionId(1L);
        review.setUserId(1L);
        review.setRating(5);
        review.setContent("测试评价");
        
        AttractionReview added = attractionService.addAttractionReview(review);
        assertNotNull(added);
        assertNotNull(added.getId());
    }

    @Test
    void testUpdateAttractionReview() {
        // 先添加一个评价
        AttractionReview review = new AttractionReview();
        review.setAttractionId(1L);
        review.setUserId(2L);
        review.setRating(4);
        review.setContent("测试评价");
        AttractionReview added = attractionService.addAttractionReview(review);
        
        // 更新评价
        added.setRating(5);
        added.setContent("更新后的测试评价");
        AttractionReview updated = attractionService.updateAttractionReview(added.getId(), added);
        assertNotNull(updated);
        assertEquals(5, updated.getRating());
        assertEquals("更新后的测试评价", updated.getContent());
    }

    @Test
    void testDeleteAttractionReview() {
        // 先添加一个评价
        AttractionReview review = new AttractionReview();
        review.setAttractionId(1L);
        review.setUserId(3L);
        review.setRating(4);
        review.setContent("测试评价");
        AttractionReview added = attractionService.addAttractionReview(review);
        
        // 删除评价
        boolean deleted = attractionService.deleteAttractionReview(added.getId());
        assertTrue(deleted);
    }

    @Test
    void testGetAttractionsByTag() {
        List<Attraction> attractions = attractionService.getAttractionsByTag("测试标签", 10);
        assertNotNull(attractions);
    }

    @Test
    void testGetAttractionStatistics() {
        Map<String, Object> statistics = attractionService.getAttractionStatistics();
        assertNotNull(statistics);
        assertTrue(statistics.containsKey("totalAttractions"));
        assertTrue(statistics.containsKey("totalCategories"));
        assertTrue(statistics.containsKey("totalReviews"));
        assertTrue(statistics.containsKey("averageRating"));
        assertTrue(statistics.containsKey("popularAttractions"));
    }

    @Test
    void testRecommendAttractions() {
        List<Attraction> attractions = attractionService.recommendAttractions(1L, 5);
        assertNotNull(attractions);
        assertTrue(attractions.size() <= 5);
    }
}