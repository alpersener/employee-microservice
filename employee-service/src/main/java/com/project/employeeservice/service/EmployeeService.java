package com.project.employeeservice.service;

import com.project.employeeservice.enums.Language;
import com.project.employeeservice.model.Employee;
import com.project.employeeservice.request.EmployeeCreateRequest;
import com.project.employeeservice.request.EmployeeUpdateRequest;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Language language, EmployeeCreateRequest employeeCreateRequest);

    Employee getEmployeeById(Language language,Long employeeId);

    List<Employee> getEmployees(Language language);

    Employee updateEmployee(Language language, Long employeeId, EmployeeUpdateRequest employeeUpdateRequest);

    Employee deleteEmployeeById(Language language,Long employeeId);
}
