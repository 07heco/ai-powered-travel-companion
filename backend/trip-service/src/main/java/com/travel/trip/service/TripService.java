package com.travel.trip.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.trip.entity.Trip;
import com.travel.trip.entity.TripDetail;

import java.util.List;

public interface TripService {
    PageResult<Trip> getUserTrips(Long userId, PageQuery query);
    Trip getTripById(Long id);
    Trip createTrip(Trip trip);
    Trip updateTrip(Trip trip);
    void deleteTrip(Long id);
    List<TripDetail> getTripDetails(Long tripId);
    TripDetail getTripDetailById(Long id);
    TripDetail createTripDetail(TripDetail tripDetail);
    TripDetail updateTripDetail(TripDetail tripDetail);
    void deleteTripDetail(Long id);
}
