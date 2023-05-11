package com.cs.services;

import com.cs.entities.Task;

import java.util.List;

public interface TaskService {
    List<Task> viewTasks(int id);
}
