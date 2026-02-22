package com.travel.ai.dao;

import com.travel.ai.entity.RecruitPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitPostDao extends JpaRepository<RecruitPost, Long> {
}