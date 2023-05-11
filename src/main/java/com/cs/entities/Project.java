package com.cs.entities;

import lombok.*;
import net.bytebuddy.agent.builder.AgentBuilder;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "project_data")
public class Project{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String projectName;
    private String projectDescription;
    private String file;

    @OneToMany
    private List<Task> taskList;

    @OneToMany
    private List<Employee> employeeList;

}
