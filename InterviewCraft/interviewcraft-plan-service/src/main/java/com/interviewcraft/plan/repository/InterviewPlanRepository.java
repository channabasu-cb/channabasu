package com.interviewcraft.plan.repository;

import com.interviewcraft.plan.model.InterviewPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewPlanRepository extends JpaRepository<InterviewPlan, Long> {
    List<InterviewPlan> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<InterviewPlan> findFirstByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<InterviewPlan> findBySessionId(String sessionId);
}
