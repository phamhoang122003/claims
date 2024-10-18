package com.spring.sevices;

import com.spring.entities.Claims;
import com.spring.entities.ProjectDetail;
import com.spring.entities.ProjectDetailKey;
import com.spring.entities.Status;
import com.spring.repository.ClaimRepository;
import com.spring.repository.ProjectDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectDetailServiceImpl implements ProjectDetailService{

    private final ProjectDetailRepository  projectDetailRepository;

    @Autowired
    private ClaimRepository claimRepository;

    public ProjectDetailServiceImpl(ProjectDetailRepository projectDetailRepository) {
        this.projectDetailRepository = projectDetailRepository;
    }

    @Override
    public List<ProjectDetail> readAll() {
        return List.of();
    }

    @Override
    public ProjectDetail readOne(ProjectDetailKey id) {
        return null;
    }

    @Override
    public List<ProjectDetail> readOneProjectId(Integer id) {

        List<ProjectDetail> projectDetail = projectDetailRepository.findByProjectId(id);

        return projectDetail;
    }

    @Override
    public ProjectDetail save(ProjectDetail projectDetail) {

        projectDetailRepository.save(projectDetail);
        return projectDetail;
    }

    @Override
    public ProjectDetail delete(ProjectDetailKey id) {
        return null;
    }




}
