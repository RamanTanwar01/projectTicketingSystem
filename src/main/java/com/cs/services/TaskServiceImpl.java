package com.cs.services;

import com.cs.entities.Project;
import com.cs.entities.Task;
import com.cs.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Task> viewTasks(int id) {
        Project project = projectRepository.findById(id);
        return project.getTaskList();
    }
}
