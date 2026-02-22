package com.travel.ai.service;

import com.travel.ai.entity.Budget;
import com.travel.ai.entity.Expense;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface BudgetService {
    /**
     * 创建预算
     * @param budget 预算信息
     * @return 创建的预算
     */
    Budget createBudget(Budget budget);

    /**
     * 获取预算详情
     * @param id 预算ID
     * @return 预算详情
     */
    Budget getBudgetById(Long id);

    /**
     * 获取用户预算列表
     * @param userId 用户ID
     * @param query 分页查询参数
     * @return 预算列表
     */
    PageResult<Budget> getUserBudgets(Long userId, PageQuery query);

    /**
     * 更新预算
     * @param budget 预算信息
     * @return 更新后的预算
     */
    Budget updateBudget(Budget budget);

    /**
     * 删除预算
     * @param id 预算ID
     */
    void deleteBudget(Long id);

    /**
     * 添加支出
     * @param expense 支出信息
     * @return 添加的支出
     */
    Expense addExpense(Expense expense);

    /**
     * 获取预算的支出列表
     * @param budgetId 预算ID
     * @param query 分页查询参数
     * @return 支出列表
     */
    PageResult<Expense> getBudgetExpenses(Long budgetId, PageQuery query);

    /**
     * 获取预算分析
     * @param budgetId 预算ID
     * @return 预算分析结果
     */
    Map<String, Object> getBudgetAnalysis(Long budgetId);

    /**
     * 获取AI智能建议
     * @param budgetId 预算ID
     * @return AI建议
     */
    String getAISuggestions(Long budgetId);

    /**
     * 预测预算
     * @param budgetId 预算ID
     * @return 预测结果
     */
    Map<String, Object> predictBudget(Long budgetId);
}