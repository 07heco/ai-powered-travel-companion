package com.travel.trip.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.trip.dao.TripDao;
import com.travel.trip.dao.TripDetailDao;
import com.travel.trip.entity.Trip;
import com.travel.trip.entity.TripDetail;
import com.travel.trip.service.TripService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Resource
    private TripDao tripDao;

    @Resource
    private TripDetailDao tripDetailDao;

    @Override
    public PageResult<Trip> getUserTrips(Long userId, PageQuery query) {
        Page<Trip> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Trip> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Trip::getUserId, userId);
        Page<Trip> result = tripDao.selectPage(page, wrapper);
        return new PageResult<Trip>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public Trip getTripById(Long id) {
        return tripDao.selectById(id);
    }

    @Override
    public Trip createTrip(Trip trip) {
        tripDao.insert(trip);
        return trip;
    }

    @Override
    public Trip updateTrip(Trip trip) {
        tripDao.updateById(trip);
        return trip;
    }

    @Override
    public void deleteTrip(Long id) {
        tripDao.deleteById(id);
        LambdaQueryWrapper<TripDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TripDetail::getTripId, id);
        tripDetailDao.delete(wrapper);
    }

    @Override
    public List<TripDetail> getTripDetails(Long tripId) {
        LambdaQueryWrapper<TripDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TripDetail::getTripId, tripId);
        wrapper.orderByAsc(TripDetail::getDay);
        return tripDetailDao.selectList(wrapper);
    }

    @Override
    public TripDetail getTripDetailById(Long id) {
        return tripDetailDao.selectById(id);
    }

    @Override
    public TripDetail createTripDetail(TripDetail tripDetail) {
        tripDetailDao.insert(tripDetail);
        return tripDetail;
    }

    @Override
    public TripDetail updateTripDetail(TripDetail tripDetail) {
        tripDetailDao.updateById(tripDetail);
        return tripDetail;
    }

    @Override
    public void deleteTripDetail(Long id) {
        tripDetailDao.deleteById(id);
    }
}
