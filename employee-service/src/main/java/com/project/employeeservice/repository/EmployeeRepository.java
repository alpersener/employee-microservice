package com.project.employeeservice.repository;

import com.project.employeeservice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee getByEmployeeIdAndDeletedFalse(Long employeeId);

    List<Employee> getAllByDeletedFalse();
}
