package com.cs.services;

import com.cs.entities.Project;

import java.util.List;

public interface ProjectService {
    void addProject(Project project,String username);
    List<Project> viewProjects();

}
