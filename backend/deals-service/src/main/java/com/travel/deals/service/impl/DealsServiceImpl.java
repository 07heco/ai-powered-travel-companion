package com.travel.deals.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import com.travel.deals.dao.DealDao;
import com.travel.deals.entity.Deal;
import com.travel.deals.service.DealsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DealsServiceImpl implements DealsService {

    @Resource
    private DealDao dealDao;

    @Override
    public Deal createDeal(Deal deal) {
        deal.setCreatedAt(LocalDateTime.now());
        deal.setUpdatedAt(LocalDateTime.now());
        dealDao.insert(deal);
        return deal;
    }

    @Override
    public Deal updateDeal(Deal deal) {
        deal.setUpdatedAt(LocalDateTime.now());
        dealDao.updateById(deal);
        return deal;
    }

    @Override
    public Deal getDealById(Long id) {
        return dealDao.selectById(id);
    }

    @Override
    public void deleteDeal(Long id) {
        dealDao.deleteById(id);
    }

    @Override
    public PageResult<Deal> getDeals(PageQuery query) {
        Page<Deal> page = new Page<>(query.getPage(), query.getSize());
        LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Deal::getPriority).orderByDesc(Deal::getCreatedAt);
        Page<Deal> result = dealDao.selectPage(page, wrapper);
        return new PageResult<Deal>(result.getTotal(), Math.toIntExact(result.getSize()), Math.toIntExact(result.getCurrent()), result.getRecords());
    }

    @Override
    public List<Deal> getActiveDeals() {
        LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Deal::getStatus, "ACTIVE");
        wrapper.gt(Deal::getEndDate, LocalDateTime.now());
        wrapper.orderByDesc(Deal::getPriority).orderByDesc(Deal::getCreatedAt);
        return dealDao.selectList(wrapper);
    }

    @Override
    public List<Deal> getDealsByType(String type) {
        LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Deal::getType, type);
        wrapper.eq(Deal::getStatus, "ACTIVE");
        wrapper.gt(Deal::getEndDate, LocalDateTime.now());
        wrapper.orderByDesc(Deal::getPriority).orderByDesc(Deal::getCreatedAt);
        return dealDao.selectList(wrapper);
    }

    @Override
    public void expireDeals() {
        LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Deal::getStatus, "ACTIVE");
        wrapper.lt(Deal::getEndDate, LocalDateTime.now());
        List<Deal> deals = dealDao.selectList(wrapper);
        for (Deal deal : deals) {
            deal.setStatus("EXPIRED");
            deal.setUpdatedAt(LocalDateTime.now());
            dealDao.updateById(deal);
        }
    }
}
