package com.project.employeecacheservice.service;

import com.project.employeecacheservice.enums.Language;
import com.project.employeecacheservice.feign.employee.EmployeeServiceFeignClient;
import com.project.employeecacheservice.model.Employee;
import com.project.employeecacheservice.repository.EmployeeRepository;
import com.project.employeecacheservice.response.EmployeeResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    private final EmployeeServiceFeignClient feignClient;
    @Override
    public Employee getEmployeeById(Language language, Long employeeId) {
        Employee employee;
        try{
            Optional<Employee> optionalEmployee=employeeRepository.findById(employeeId);
            if (optionalEmployee.isPresent()) {
                employee=optionalEmployee.get();
            }else{
                log.info("Data came from employee-service");
                EmployeeResponse response=feignClient.getEmployee(language,employeeId).getPayload();
                employee= Employee.builder()
                        .employeeId(response.getEmployeeId())
                        .firstName(response.getFirstName())
                        .lastName(response.getLastName())
                        .email(response.getEmail())
                        .salary(response.getSalary())
                        .position(response.getPosition())
                        .department(response.getDepartment())
                        .startDate(response.getStartDate())
                        .endDate(response.getEndDate())
                        .build();
                employeeRepository.save(employee);
            }
        }
        catch (FeignException.FeignClientException.NotFound exc){
            throw new NotFoundException("Employee not found for employeeId:"+employeeId);
        }
        return employee;
    }

    @Override
    public void deleteEmployee(Language language) {
        employeeRepository.deleteAll();
        log.info("Deleted all employees");

    }
}
