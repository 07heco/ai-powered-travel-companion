package com.travel.ai.dao;

import com.travel.ai.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetDao extends JpaRepository<Budget, Long> {
}