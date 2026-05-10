package com.travel.deals.service;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.deals.entity.Deal;

import java.util.List;

public interface DealsService {
    Deal createDeal(Deal deal);
    Deal updateDeal(Deal deal);
    Deal getDealById(Long id);
    void deleteDeal(Long id);
    PageResult<Deal> getDeals(PageQuery query);
    List<Deal> getActiveDeals();
    List<Deal> getDealsByType(String type);
    void expireDeals();
}
