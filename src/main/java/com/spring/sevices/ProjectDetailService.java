package com.spring.sevices;

import com.spring.entities.Claims;
import com.spring.entities.ProjectDetail;
import com.spring.entities.ProjectDetailKey;

import java.util.List;

public interface ProjectDetailService {
    List<ProjectDetail> readAll();
    ProjectDetail readOne(ProjectDetailKey id);
    List<ProjectDetail> readOneProjectId(Integer id);
    ProjectDetail save(ProjectDetail projectDetail);
    ProjectDetail delete(ProjectDetailKey id);
    //ProjectDetail update(Integer id,String roleInProject, String role  );





}
