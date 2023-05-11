package com.cs.services;

import com.cs.entities.Employee;

import java.util.List;


public interface EmployeeService {
   void addEmployee(Employee employee);

   List<Employee> viewDevelopers();


}
