package com.interviewcraft.plan.repository;

import com.interviewcraft.plan.model.PlanMilestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanMilestoneRepository extends JpaRepository<PlanMilestone, Long> {
    List<PlanMilestone> findByPlanIdOrderBySequenceOrderAsc(Long planId);
}
