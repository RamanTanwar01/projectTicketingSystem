package com.cs.repositories;

import com.cs.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("select e from Employee e where e.email = :email")
    public Employee getEmployeeByEmail(@Param("email") String email);

    int countByEmail(String email);

}
