package com.spring.repository;

import com.spring.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {

    @Query("SELECT p FROM Project p WHERE p.projectCode LIKE :search OR p.projectName LIKE :search ")
    Page<Project> findBySearch(@Param("search") String search, Pageable pageable);

    Project findByProjectName(String name);
    List<Project> findAllById(Iterable<Integer> ids);

    Optional<Project> findById (Integer id);
}
