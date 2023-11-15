package com.project.employeecacheservice.controller;

import com.project.employeecacheservice.enums.Language;
import com.project.employeecacheservice.model.Employee;
import com.project.employeecacheservice.response.EmployeeResponse;
import com.project.employeecacheservice.response.InternalApiResponse;
import com.project.employeecacheservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/employee-cache")
public class EmployeeCacheController {
    private final EmployeeService employeeService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/get/{employeeId}")
    public InternalApiResponse<EmployeeResponse>getEmployee(@PathVariable("language")Language language,
                                                            @PathVariable("employeeId")Long employeeId){
        log.debug("[{}][getEmployee]->request employeeId:{}",this.getClass().getSimpleName(),employeeId);
        Employee employee=employeeService.getEmployeeById(language,employeeId);
        EmployeeResponse response=convertEmployeeResponse(employee);
        log.debug("[{}][getEmployee]->response employeeId:{}",this.getClass().getSimpleName(),response);
        return InternalApiResponse.<EmployeeResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/removeAllEmployees")
    public void removeEmployee(@PathVariable("language")Language language){
        employeeService.deleteEmployee(language);
    }


    private static EmployeeResponse convertEmployeeResponse(Employee employee){
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .position(employee.getPosition())
                .department(employee.getDepartment())
                .salary(employee.getSalary())
                .startDate(employee.getStartDate())
                .endDate(employee.getEndDate())
                .build();
    }
}
