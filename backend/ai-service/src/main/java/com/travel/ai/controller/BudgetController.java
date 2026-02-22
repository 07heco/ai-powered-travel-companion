package com.travel.ai.controller;

import com.travel.common.vo.PageQuery;
import com.travel.common.vo.R;
import com.travel.ai.entity.Budget;
import com.travel.ai.entity.Expense;
import com.travel.ai.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("")
@Tag(name = "预算服务", description = "提供智能预算管理相关接口，包括预算创建、支出记录、预算分析和AI智能建议功能")
public class BudgetController {

    @Resource
    private BudgetService budgetService;

    @Operation(
            summary = "创建预算", 
            description = "创建新的旅行预算，设置总预算、目的地、旅行日期等信息"
    )
    @PostMapping("/budget/create")
    public R<?> createBudget(@RequestBody Budget budget) {
        return R.success(budgetService.createBudget(budget));
    }

    @Operation(
            summary = "获取预算详情", 
            description = "根据ID获取预算的详细信息，包括总预算、已花费金额、剩余金额等"
    )
    @GetMapping("/budget/{id}")
    public R<?> getBudgetById(@PathVariable Long id) {
        return R.success(budgetService.getBudgetById(id));
    }

    @Operation(
            summary = "获取用户预算列表", 
            description = "分页获取用户的预算列表，支持按创建时间排序"
    )
    @GetMapping("/budget/user/{userId}")
    public R<?> getUserBudgets(@PathVariable Long userId, PageQuery query) {
        return R.success(budgetService.getUserBudgets(userId, query));
    }

    @Operation(
            summary = "更新预算", 
            description = "更新预算信息，如总预算、旅行日期、目的地等"
    )
    @PutMapping("/budget/update")
    public R<?> updateBudget(@RequestBody Budget budget) {
        return R.success(budgetService.updateBudget(budget));
    }

    @Operation(
            summary = "删除预算", 
            description = "根据ID删除指定的预算"
    )
    @DeleteMapping("/budget/{id}")
    public R<?> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        return R.success();
    }

    @Operation(
            summary = "添加支出", 
            description = "添加新的支出记录，包括分类、金额、描述等信息"
    )
    @PostMapping("/budget/expense/add")
    public R<?> addExpense(@RequestBody Expense expense) {
        return R.success(budgetService.addExpense(expense));
    }

    @Operation(
            summary = "获取预算支出列表", 
            description = "分页获取指定预算的支出记录列表，支持按日期排序"
    )
    @GetMapping("/budget/expense/{budgetId}")
    public R<?> getBudgetExpenses(@PathVariable Long budgetId, PageQuery query) {
        return R.success(budgetService.getBudgetExpenses(budgetId, query));
    }

    @Operation(
            summary = "获取预算分析", 
            description = "获取预算的详细分析结果，包括支出分类统计、预算使用情况等"
    )
    @GetMapping("/budget/analysis/{budgetId}")
    public R<?> getBudgetAnalysis(@PathVariable Long budgetId) {
        return R.success(budgetService.getBudgetAnalysis(budgetId));
    }

    @Operation(
            summary = "获取AI智能建议", 
            description = "获取AI对预算的智能建议，包括省钱技巧、预算调整建议等"
    )
    @GetMapping("/budget/ai/suggestions/{budgetId}")
    public R<?> getAISuggestions(@PathVariable Long budgetId) {
        return R.success(budgetService.getAISuggestions(budgetId));
    }

    @Operation(
            summary = "预测预算", 
            description = "预测预算的使用情况，包括预计总花费、是否超预算等"
    )
    @GetMapping("/budget/predict/{budgetId}")
    public R<?> predictBudget(@PathVariable Long budgetId) {
        return R.success(budgetService.predictBudget(budgetId));
    }
}