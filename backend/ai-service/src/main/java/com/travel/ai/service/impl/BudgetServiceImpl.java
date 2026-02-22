package com.travel.ai.service.impl;

import com.travel.ai.dao.BudgetDao;
import com.travel.ai.dao.ExpenseDao;
import com.travel.ai.entity.Budget;
import com.travel.ai.entity.Expense;
import com.travel.ai.service.BudgetService;
import com.travel.common.vo.PageQuery;
import com.travel.common.vo.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Resource
    private BudgetDao budgetDao;

    @Resource
    private ExpenseDao expenseDao;

    @Override
    public Budget createBudget(Budget budget) {
        budget.setSpentAmount(0.0);
        budget.setStatus("ACTIVE");
        budget.setCreatedAt(LocalDateTime.now());
        budget.setUpdatedAt(LocalDateTime.now());
        return budgetDao.save(budget);
    }

    @Override
    public Budget getBudgetById(Long id) {
        return budgetDao.findById(id).orElse(null);
    }

    @Override
    public PageResult<Budget> getUserBudgets(Long userId, PageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getSize());
        Page<Budget> result = budgetDao.findAll(pageRequest);
        return new PageResult<>(result.getTotalElements(), result.getSize(), 
                result.getNumber() + 1, result.getContent());
    }

    @Override
    public Budget updateBudget(Budget budget) {
        budget.setUpdatedAt(LocalDateTime.now());
        return budgetDao.save(budget);
    }

    @Override
    public void deleteBudget(Long id) {
        budgetDao.deleteById(id);
    }

    @Override
    public Expense addExpense(Expense expense) {
        expense.setCreatedAt(LocalDateTime.now());
        Expense savedExpense = expenseDao.save(expense);

        // 更新预算的已花费金额
        Budget budget = budgetDao.findById(expense.getBudgetId()).orElse(null);
        if (budget != null) {
            budget.setSpentAmount(budget.getSpentAmount() + expense.getAmount());
            budget.setUpdatedAt(LocalDateTime.now());
            budgetDao.save(budget);
        }

        return savedExpense;
    }

    @Override
    public PageResult<Expense> getBudgetExpenses(Long budgetId, PageQuery query) {
        PageRequest pageRequest = PageRequest.of(query.getPage() - 1, query.getSize());
        Page<Expense> result = expenseDao.findAll(pageRequest);
        List<Expense> filteredExpenses = result.getContent().stream()
                .filter(expense -> expense.getBudgetId().equals(budgetId))
                .collect(Collectors.toList());
        return new PageResult<>((long) filteredExpenses.size(), query.getSize(), query.getPage(), filteredExpenses);
    }

    @Override
    public Map<String, Object> getBudgetAnalysis(Long budgetId) {
        Budget budget = budgetDao.findById(budgetId).orElse(null);
        if (budget == null) {
            return new HashMap<>();
        }

        // 获取所有支出
        List<Expense> expenses = expenseDao.findAll().stream()
                .filter(expense -> expense.getBudgetId().equals(budgetId))
                .collect(Collectors.toList());

        // 按分类统计
        Map<String, Double> categoryStats = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, 
                        Collectors.summingDouble(Expense::getAmount)));

        // 计算总花费和剩余预算
        double totalSpent = budget.getSpentAmount();
        double remaining = budget.getTotalBudget() - totalSpent;
        double spentPercentage = (totalSpent / budget.getTotalBudget()) * 100;

        // 构建分析结果
        Map<String, Object> analysis = new HashMap<>();
        analysis.put("budget", budget);
        analysis.put("totalSpent", totalSpent);
        analysis.put("remaining", remaining);
        analysis.put("spentPercentage", spentPercentage);
        analysis.put("categoryStats", categoryStats);
        analysis.put("expenseCount", expenses.size());

        // 计算日均支出
        if (!expenses.isEmpty()) {
            double dailyAverage = totalSpent / expenses.size();
            analysis.put("dailyAverage", dailyAverage);
        }

        return analysis;
    }

    @Override
    public String getAISuggestions(Long budgetId) {
        Budget budget = budgetDao.findById(budgetId).orElse(null);
        if (budget == null) {
            return "Budget not found";
        }

        Map<String, Object> analysis = getBudgetAnalysis(budgetId);
        double spentPercentage = (double) analysis.get("spentPercentage");
        Map<String, Double> categoryStats = (Map<String, Double>) analysis.get("categoryStats");

        // 模拟AI智能建议
        StringBuilder suggestions = new StringBuilder();
        suggestions.append("# AI智能预算建议\n\n")
                .append("## 预算健康评估\n");

        if (spentPercentage < 30) {
            suggestions.append("✅ 预算健康状况良好，支出控制优秀\n");
        } else if (spentPercentage < 70) {
            suggestions.append("⚠️ 预算健康状况适中，继续保持当前消费节奏\n");
        } else {
            suggestions.append("❌ 预算健康状况不佳，建议控制支出\n");
        }

        suggestions.append("\n## 分类支出建议\n");
        for (Map.Entry<String, Double> entry : categoryStats.entrySet()) {
            String category = entry.getKey();
            double amount = entry.getValue();
            double categoryPercentage = (amount / budget.getTotalBudget()) * 100;

            suggestions.append("### " + category + "\n");
            if (categoryPercentage > 30) {
                suggestions.append("- 支出占比较高，建议适当控制\n");
            } else {
                suggestions.append("- 支出占比合理，继续保持\n");
            }
        }

        suggestions.append("\n## 省钱小技巧\n")
                .append("1. 优先选择当地特色小吃，既省钱又能体验当地文化\n")
                .append("2. 购买景点联票，比单独购买更划算\n")
                .append("3. 使用公共交通工具，避免打车\n")
                .append("4. 提前预订住宿，通常能获得折扣\n")
                .append("5. 携带必备物品，避免临时购买\n");

        if (spentPercentage > 70) {
            suggestions.append("\n## 警告\n")
                    .append("- 您的预算已使用超过70%，建议减少非必要支出\n")
                    .append("- 可考虑调整后续行程，选择免费或低成本的活动\n");
        }

        return suggestions.toString();
    }

    @Override
    public Map<String, Object> predictBudget(Long budgetId) {
        Budget budget = budgetDao.findById(budgetId).orElse(null);
        if (budget == null) {
            return new HashMap<>();
        }

        Map<String, Object> analysis = getBudgetAnalysis(budgetId);
        double spentPercentage = (double) analysis.get("spentPercentage");
        double dailyAverage = analysis.containsKey("dailyAverage") ? (double) analysis.get("dailyAverage") : 0;

        // 简单预测：基于当前日均支出
        // 实际项目中可以使用更复杂的预测模型
        int daysRemaining = 3; // 假设还剩3天
        double projectedTotal = budget.getSpentAmount() + (dailyAverage * daysRemaining);
        double projectedOverBudget = Math.max(0, projectedTotal - budget.getTotalBudget());
        String predictionStatus = projectedOverBudget > 0 ? "OVER_BUDGET" : "ON_TRACK";

        Map<String, Object> prediction = new HashMap<>();
        prediction.put("budget", budget);
        prediction.put("currentSpent", budget.getSpentAmount());
        prediction.put("dailyAverage", dailyAverage);
        prediction.put("daysRemaining", daysRemaining);
        prediction.put("projectedTotal", projectedTotal);
        prediction.put("projectedOverBudget", projectedOverBudget);
        prediction.put("predictionStatus", predictionStatus);
        prediction.put("recommendation", projectedOverBudget > 0 ? 
                "建议减少非必要支出，控制每日花费在 " + (budget.getTotalBudget() - budget.getSpentAmount()) / daysRemaining + " 元以内" : 
                "支出控制良好，继续保持");

        return prediction;
    }
}