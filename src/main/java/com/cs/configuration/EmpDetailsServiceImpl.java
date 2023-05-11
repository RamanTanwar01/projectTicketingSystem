package com.cs.configuration;

import com.cs.entities.Employee;
import com.cs.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmpDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetching employee from Database
        Employee employee = employeeRepository.getEmployeeByEmail(username);
        if(employee == null){
            throw new UsernameNotFoundException("Could not found employee..!!");
        }
        return new CustomEmpDetails(employee);
    }
}
