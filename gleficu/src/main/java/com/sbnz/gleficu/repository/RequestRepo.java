package com.sbnz.gleficu.repository;

import com.sbnz.gleficu.model.RecommendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<RecommendRequest, Integer> {
}
