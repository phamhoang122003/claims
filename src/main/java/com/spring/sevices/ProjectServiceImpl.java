package com.spring.sevices;

import com.spring.entities.Project;
import com.spring.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public List<Project> readAll() {
        List<Project> projects = projectRepository.findAll();
        return projects;
    }

    @Override
    public Project readOne(Integer id) {
        Project project = projectRepository.findById(id).orElse(null);

        return project;
    }

    @Override
    public Project save(Project project) {
        projectRepository.save(project);
        return project;
    }

    @Override
    public Project delete(Integer id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project!= null) {
            projectRepository.delete(project);
        }
        return project;
    }
}
