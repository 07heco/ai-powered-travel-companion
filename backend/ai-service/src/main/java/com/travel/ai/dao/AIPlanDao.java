package com.travel.ai.dao;

import com.travel.ai.entity.AIPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIPlanDao extends JpaRepository<AIPlan, Long> {
}
