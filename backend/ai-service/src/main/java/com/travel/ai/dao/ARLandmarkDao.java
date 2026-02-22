package com.travel.ai.dao;

import com.travel.ai.entity.ARLandmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ARLandmarkDao extends JpaRepository<ARLandmark, Long> {
}