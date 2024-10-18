package com.spring.sevices;

import com.spring.entities.Project;

import java.util.List;

public interface ProjectService {

    List<Project> readAll();
    Project readOne(Integer id);
    Project save(Project project);
    Project delete(Integer id);
}
