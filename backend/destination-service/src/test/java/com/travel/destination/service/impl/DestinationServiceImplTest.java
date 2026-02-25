package com.travel.destination.service.impl;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.destination.entity.Attraction;
import com.travel.destination.entity.Destination;
import com.travel.destination.service.DestinationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DestinationServiceImplTest {

    @Autowired
    private DestinationService destinationService;

    @Test
    void testGetDestinations() {
        PageQuery query = new PageQuery();
        query.setPage(1);
        query.setSize(10);
        
        PageResult<Destination> result = destinationService.getDestinations(query);
        assertNotNull(result);
        assertNotNull(result.getRecords());
        assertTrue(result.getTotal() >= 0);
    }

    @Test
    void testGetDestinationById() {
        // 测试获取已存在的目的地
        Destination destination = destinationService.getDestinationById(1L);
        assertNotNull(destination);
        assertEquals("北京", destination.getName());
    }

    @Test
    void testGetAttractionsByDestinationId() {
        List<Attraction> attractions = destinationService.getAttractionsByDestinationId(1L);
        assertNotNull(attractions);
        assertTrue(attractions.size() > 0);
        // 验证所有景点都属于指定的目的地
        for (Attraction attraction : attractions) {
            assertEquals(1L, attraction.getDestinationId());
        }
    }

    @Test
    void testGetPopularDestinations() {
        List<Destination> destinations = destinationService.getPopularDestinations(5);
        assertNotNull(destinations);
        assertTrue(destinations.size() <= 5);
    }

    @Test
    void testSearchDestinations() {
        List<Destination> destinations = destinationService.searchDestinations("北京");
        assertNotNull(destinations);
        assertTrue(destinations.size() > 0);
        // 验证搜索结果包含关键词
        for (Destination destination : destinations) {
            assertTrue(destination.getName().contains("北京") || 
                       destination.getDescription().contains("北京"));
        }
    }
}