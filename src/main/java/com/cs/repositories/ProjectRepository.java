package com.cs.repositories;

import com.cs.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProjectRepository extends JpaRepository<Project,Integer> {
    int countByprojectName(String projectName);

    Project findByprojectName(String projectName);

    Project findById(int projectId);


}
