package com.travel.ai.dao;

import com.travel.ai.entity.TravelMate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelMateDao extends JpaRepository<TravelMate, Long> {
}
