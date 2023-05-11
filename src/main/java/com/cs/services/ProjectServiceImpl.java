package com.cs.services;

import com.cs.entities.Employee;
import com.cs.entities.Project;
import com.cs.repositories.EmployeeRepository;
import com.cs.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private  EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public void addProject(Project project,String username) {
        Employee employee = employeeRepository.getEmployeeByEmail(username);
        List<Project> list = employee.getProjectList();
        list.add(project);
        employee.setProjectList(list);
        employeeRepository.save(employee);
    }

    @Override
    public List<Project> viewProjects() {
        return projectRepository.findAll();
    }


}
