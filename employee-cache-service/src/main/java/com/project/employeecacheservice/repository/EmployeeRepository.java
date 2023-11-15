package com.project.employeecacheservice.repository;

import com.project.employeecacheservice.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {
}
