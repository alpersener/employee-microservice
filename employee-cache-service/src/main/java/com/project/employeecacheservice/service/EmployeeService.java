package com.project.employeecacheservice.service;

import com.project.employeecacheservice.enums.Language;
import com.project.employeecacheservice.model.Employee;

public interface EmployeeService {
    Employee getEmployeeById(Language language,Long employeeId);

    void deleteEmployee(Language language);

}
