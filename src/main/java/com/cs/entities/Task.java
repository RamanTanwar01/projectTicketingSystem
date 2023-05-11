package com.cs.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "task_data")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;
    private String title;
    private String description;
    private String priority;
    private String assignee;
    private String assignedTo;
    private String status;
    private String comments;
    private String tags;
    private LocalDateTime creationDate;
    private Boolean completed;
    private LocalDateTime deadline;

//    @OneToOne(mappedBy = "task",cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private Employee employee;


}
