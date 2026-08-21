package com.interviewcraft.plan.repository;

import com.interviewcraft.plan.model.PlanTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanTaskRepository extends JpaRepository<PlanTask, Long> {
    List<PlanTask> findByMilestoneIdOrderByDayNumberAsc(Long milestoneId);
}
