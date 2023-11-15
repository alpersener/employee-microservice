package com.project.employeeservice.service;

import com.project.employeeservice.enums.Language;
import com.project.employeeservice.exception.enums.FriendlyMessageCodeImpl;
import com.project.employeeservice.exception.exceptions.EmployeeAlreadyDeletedException;
import com.project.employeeservice.exception.exceptions.EmployeeNotCreatedException;
import com.project.employeeservice.exception.exceptions.EmployeeNotFoundException;
import com.project.employeeservice.model.Employee;
import com.project.employeeservice.repository.EmployeeRepository;
import com.project.employeeservice.request.EmployeeCreateRequest;
import com.project.employeeservice.request.EmployeeUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee createEmployee(Language language, EmployeeCreateRequest employeeCreateRequest) {
        log.debug("[{}][createEmployee]->request:{}",this.getClass().getSimpleName(),employeeCreateRequest);
        try {
            Employee employee= Employee.builder()
                    .firstName(employeeCreateRequest.getFirstName())
                    .lastName(employeeCreateRequest.getLastName())
                    .email(employeeCreateRequest.getEmail())
                    .department(employeeCreateRequest.getDepartment())
                    .salary(employeeCreateRequest.getSalary())
                    .position(employeeCreateRequest.getPosition())
                    .startDate(new Date())
                    .endDate(new Date())
                    .deleted(false)
                    .isActive(true)
                    .build();
            Employee employeeResponse=employeeRepository.save(employee);
            log.debug("[{}][createEmployee]->response:{}",this.getClass().getSimpleName(),employeeResponse);
            return employeeResponse;
        }catch (Exception exception){
            throw new EmployeeNotCreatedException(language
                    , FriendlyMessageCodeImpl.EMPLOYEE_NOT_CREATED_EXCEPTION
                    ,"employee request:"+employeeCreateRequest.toString());
        }
    }

    @Override
    public Employee getEmployeeById(Language language, Long employeeId) {
        log.debug("[{}][getEmployeeById]->request:{}",this.getClass().getSimpleName(),employeeId);
        Employee employee=employeeRepository.getByEmployeeIdAndDeletedFalse(employeeId);
        if (Objects.isNull(employee)) {
            throw new EmployeeNotFoundException(language
                    ,FriendlyMessageCodeImpl.EMPLOYEE_NOT_FOUND
                    ,"Employee not found for employeeId:"+employeeId);
        }
        log.debug("[{}][getEmployeeById]->response:{}",this.getClass().getSimpleName(),employee);
        return employee;
    }

    @Override
    public List<Employee> getEmployees(Language language) {
        log.debug("[{}][getEmployees]",this.getClass().getSimpleName());
        List<Employee> employees=employeeRepository.getAllByDeletedFalse();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException(language
                    ,FriendlyMessageCodeImpl.EMPLOYEE_NOT_FOUND
                    ,"Employees Not found");
        }
        log.debug("[{}][getEmployees]->response:{}",this.getClass().getSimpleName(),employees);
        return employees;
    }

    @Override
    public Employee updateEmployee(Language language, Long employeeId, EmployeeUpdateRequest employeeUpdateRequest) {
        log.debug("[{}][updateEmployee]->request:{}",this.getClass().getSimpleName(),employeeUpdateRequest);
        Employee employee=getEmployeeById(language,employeeId);
        employee.setFirstName(employeeUpdateRequest.getFirstName());
        employee.setLastName(employeeUpdateRequest.getLastName());
        employee.setEmail(employeeUpdateRequest.getEmail());
        employee.setDepartment(employeeUpdateRequest.getDepartment());
        employee.setSalary(employeeUpdateRequest.getSalary());
        employee.setPosition(employeeUpdateRequest.getPosition());
        employee.setStartDate(employee.getStartDate());
        employee.setEndDate(new Date());
        Employee employeeResponse=employeeRepository.save(employee);
        log.debug("[{}][updateEmployee]->response:{}",this.getClass().getSimpleName(),employeeResponse);
        return employeeResponse;
    }

    @Override
    public Employee deleteEmployeeById(Language language, Long employeeId) {
        log.debug("[{}][deleteEmployeeById]->request:{}",this.getClass().getSimpleName(),employeeId);
        Employee employee;
        try {
            employee=getEmployeeById(language,employeeId);
            employee.setDeleted(true);
            employee.setActive(false);
            employee.setEndDate(new Date());
            Employee employeeResponse=employeeRepository.save(employee);
            log.debug("[{}][deleteEmployeeById]->response:{}",this.getClass().getSimpleName(),employeeResponse);
            return employeeResponse;
        }catch (EmployeeNotFoundException employeeNotFoundException){
            throw new EmployeeAlreadyDeletedException(language
                    ,FriendlyMessageCodeImpl.EMPLOYEE_ALREADY_DELETED
                    ,"Employeee Already Deleted employeeId:"+employeeId);
        }


    }
}
