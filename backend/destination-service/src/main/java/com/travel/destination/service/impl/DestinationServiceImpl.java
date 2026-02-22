package com.travel.destination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.destination.dao.DestinationDao;
import com.travel.destination.dao.AttractionDao;
import com.travel.destination.entity.Destination;
import com.travel.destination.entity.Attraction;
import com.travel.destination.service.DestinationService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {

    @Resource
    private DestinationDao destinationDao;

    @Resource
    private AttractionDao attractionDao;

    @Override
    public PageResult<Destination> getDestinations(PageQuery query) {
        Page<Destination> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Destination> wrapper = new LambdaQueryWrapper<>();
        Page<Destination> result = destinationDao.selectPage(page, wrapper);
        return new PageResult<Destination>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public Destination getDestinationById(Long id) {
        return destinationDao.selectById(id);
    }

    @Override
    public List<Attraction> getAttractionsByDestinationId(Long destinationId) {
        LambdaQueryWrapper<Attraction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Attraction::getDestinationId, destinationId);
        return attractionDao.selectList(wrapper);
    }

    @Override
    public List<Destination> getPopularDestinations(Integer limit) {
        LambdaQueryWrapper<Destination> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Destination::getPopularScore);
        wrapper.last("LIMIT " + limit);
        return destinationDao.selectList(wrapper);
    }

    @Override
    public List<Destination> searchDestinations(String keyword) {
        LambdaQueryWrapper<Destination> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Destination::getName, keyword)
                .or().like(Destination::getCountry, keyword)
                .or().like(Destination::getCity, keyword);
        return destinationDao.selectList(wrapper);
    }
}
