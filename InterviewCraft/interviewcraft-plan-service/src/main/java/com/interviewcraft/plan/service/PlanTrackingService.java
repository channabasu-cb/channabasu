package com.interviewcraft.plan.service;

import com.interviewcraft.common.dto.InterviewPlanDto;
import com.interviewcraft.common.dto.TaskDto;
import com.interviewcraft.common.exception.ResourceNotFoundException;
import com.interviewcraft.plan.model.InterviewPlan;
import com.interviewcraft.plan.model.PlanMilestone;
import com.interviewcraft.plan.model.PlanTask;
import com.interviewcraft.plan.repository.InterviewPlanRepository;
import com.interviewcraft.plan.repository.PlanMilestoneRepository;
import com.interviewcraft.plan.repository.PlanTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanTrackingService {

    private final PlanTaskRepository taskRepository;
    private final PlanMilestoneRepository milestoneRepository;
    private final InterviewPlanRepository planRepository;
    private final RoadmapGeneratorService roadmapGeneratorService;

    @Transactional
    public TaskDto toggleTask(Long taskId) {
        PlanTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("PlanTask", "id", taskId));

        boolean newStatus = !task.isCompleted();
        task.setCompleted(newStatus);
        task.setCompletedAt(newStatus ? LocalDateTime.now() : null);
        PlanTask saved = taskRepository.save(task);

        // Update parent milestone and plan progress
        PlanMilestone milestone = task.getMilestone();
        if (milestone != null) {
            List<PlanTask> tasks = taskRepository.findByMilestoneIdOrderByDayNumberAsc(milestone.getId());
            boolean allCompleted = tasks.stream().allMatch(PlanTask::isCompleted);
            milestone.setCompleted(allCompleted);
            milestoneRepository.save(milestone);

            InterviewPlan plan = milestone.getPlan();
            if (plan != null) {
                recalculatePlanProgress(plan);
            }
        }

        return TaskDto.builder()
                .id(saved.getId())
                .milestoneId(saved.getMilestone() != null ? saved.getMilestone().getId() : null)
                .dayNumber(saved.getDayNumber())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .category(saved.getCategory())
                .estimatedMinutes(saved.getEstimatedMinutes())
                .completed(saved.isCompleted())
                .resourceUrl(saved.getResourceUrl())
                .completedAt(saved.getCompletedAt())
                .build();
    }

    private void recalculatePlanProgress(InterviewPlan plan) {
        List<PlanMilestone> milestones = milestoneRepository.findByPlanIdOrderBySequenceOrderAsc(plan.getId());
        int totalTasks = 0;
        int completedTasks = 0;
        int completedMinutes = 0;

        for (PlanMilestone m : milestones) {
            List<PlanTask> tasks = taskRepository.findByMilestoneIdOrderByDayNumberAsc(m.getId());
            totalTasks += tasks.size();
            for (PlanTask t : tasks) {
                if (t.isCompleted()) {
                    completedTasks++;
                    completedMinutes += (t.getEstimatedMinutes() != null ? t.getEstimatedMinutes() : 60);
                }
            }
        }

        int completedHours = (int) Math.ceil(completedMinutes / 60.0);
        int readinessScore = totalTasks > 0 ? (int) Math.round(((double) completedTasks / totalTasks) * 100) : 0;

        plan.setCompletedHours(completedHours);
        plan.setReadinessScore(readinessScore);
        if (readinessScore == 100) {
            plan.setStatus("COMPLETED");
        } else if (plan.getStatus().equals("COMPLETED")) {
            plan.setStatus("IN_PROGRESS");
        }

        planRepository.save(plan);
        log.info("Recalculated progress for Plan {}: {}% ({} completed tasks out of {})",
                plan.getId(), readinessScore, completedTasks, totalTasks);
    }
}
