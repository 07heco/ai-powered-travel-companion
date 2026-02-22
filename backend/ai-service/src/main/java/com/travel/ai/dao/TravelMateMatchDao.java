package com.travel.ai.dao;

import com.travel.ai.entity.TravelMateMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelMateMatchDao extends JpaRepository<TravelMateMatch, Long> {
}