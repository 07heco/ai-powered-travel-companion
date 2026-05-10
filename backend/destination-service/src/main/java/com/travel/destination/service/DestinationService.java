package com.travel.destination.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.destination.entity.Destination;
import com.travel.destination.entity.Attraction;

import java.util.List;

public interface DestinationService {
    PageResult<Destination> getDestinations(PageQuery query);
    Destination getDestinationById(Long id);
    List<Attraction> getAttractionsByDestinationId(Long destinationId);
    List<Destination> getPopularDestinations(Integer limit);
    List<Destination> searchDestinations(String keyword);
}
