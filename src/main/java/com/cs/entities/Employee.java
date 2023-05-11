package com.cs.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee_registration_data")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;
    @NotBlank(message = "First Name is required..!!")
    @Size(min = 2,max = 15, message = "First Name should be between 3-15 characters")
    private String firstName;
    @NotBlank(message = "Last Name is required..!!")
    @Size(min = 2,max = 15, message = "Last Name should be between 3-15 characters")
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String skills;


    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Project> projectList;

    @OneToOne
    @JsonBackReference
    private Task task;
}
