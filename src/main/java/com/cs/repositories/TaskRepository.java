package com.cs.repositories;

import com.cs.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    int countBytitle(String title);
}
