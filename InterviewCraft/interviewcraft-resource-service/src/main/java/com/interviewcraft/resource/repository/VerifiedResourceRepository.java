package com.interviewcraft.resource.repository;

import com.interviewcraft.resource.model.VerifiedResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerifiedResourceRepository extends JpaRepository<VerifiedResource, Long> {
    Optional<VerifiedResource> findByUrl(String url);
    List<VerifiedResource> findByCategoryIgnoreCase(String category);
    List<VerifiedResource> findByIsVerifiedWorkingTrue();

    @Query("SELECT r FROM VerifiedResource r JOIN r.topics t WHERE LOWER(t) LIKE LOWER(CONCAT('%', :topic, '%'))")
    List<VerifiedResource> findByTopicContainingIgnoreCase(@Param("topic") String topic);

    @Query("SELECT r FROM VerifiedResource r WHERE " +
            "LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.authorOrChannel) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<VerifiedResource> searchResources(@Param("query") String query);
}
