package com.spring.repository;

import com.spring.entities.Claims;
import com.spring.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claims, Integer> {
    @Query(value = "SELECT * FROM claims WHERE id IN :ids", nativeQuery = true)
    List<Claims> findAllById(List<Integer> ids);

    List<Claims> findByStatusNot(Status status);

    List<Claims> findAllByProject_Id(Integer projectId);

    @Query("SELECT c.status, COUNT(c) FROM Claims c GROUP BY c.status")
    List<Object[]> countClaimsByStatus();

    @Query("SELECT c.status, COUNT(c.id) FROM Claims c WHERE c.project.id =:id GROUP BY c.status")
    List<Object[]> countClaimsByStatusAndProjectId(@Param("id") Integer id);
}