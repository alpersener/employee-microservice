package com.project.employeeservice.controller;

import com.project.employeeservice.enums.Language;
import com.project.employeeservice.exception.enums.FriendlyMessageCode;
import com.project.employeeservice.exception.enums.FriendlyMessageCodeImpl;
import com.project.employeeservice.exception.utils.FriendlyMessageUtils;
import com.project.employeeservice.model.Employee;
import com.project.employeeservice.request.EmployeeCreateRequest;
import com.project.employeeservice.request.EmployeeUpdateRequest;
import com.project.employeeservice.response.EmployeeResponse;
import com.project.employeeservice.response.FriendlyMessage;
import com.project.employeeservice.response.InternalApiResponse;
import com.project.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @Operation(description = "This endpoint creates an employee record")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{language}/create")
    public InternalApiResponse<EmployeeResponse> createEmployee(@PathVariable("language") Language language,
                                                               @RequestBody EmployeeCreateRequest employeeCreateRequest)
    {
        log.debug("[{}] [createEmployee]->request:{}",this.getClass().getSimpleName(),employeeCreateRequest);
        Employee employee=employeeService.createEmployee(language,employeeCreateRequest);
        EmployeeResponse employeeResponse=convertEmployeeResponse(employee);
        log.debug("[{}] [createEmployee]->response:{}",this.getClass().getSimpleName(),employeeResponse);
        return InternalApiResponse.<EmployeeResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language
                                , FriendlyMessageCodeImpl.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language
                                , FriendlyMessageCodeImpl.EMPLOYEE_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(employeeResponse)
                .build();
    }

    @Operation(description = "This endpoint retrieves information about an employee record")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/get/{employeeId}")
    public InternalApiResponse<EmployeeResponse> getEmployeeById(@PathVariable("language")Language language,
                                                                @PathVariable("employeeId") Long employeeId){
        log.debug("[{}] [getEmployeeById]->request:{}",this.getClass().getSimpleName(),employeeId);
        Employee employee=employeeService.getEmployeeById(language,employeeId);
        EmployeeResponse employeeResponse=convertEmployeeResponse(employee);
        log.debug("[{}] [getEmployeeById]->response:{}",this.getClass().getSimpleName(),employeeResponse);
        return InternalApiResponse.<EmployeeResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(employeeResponse)
                .build();
    }

    @Operation(description = "This Endpoint updates an employee record")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{language}/update/{employeeId}")
    public InternalApiResponse<EmployeeResponse> updateEmployee(@PathVariable("language")Language language,
                                                                @PathVariable("employeeId") Long employeeId,
                                                                @RequestBody EmployeeUpdateRequest request){
        log.debug("[{}] [updateEmployee]->request:{} {}",this.getClass().getSimpleName(),employeeId,request);
        Employee employee=employeeService.updateEmployee(language,employeeId,request);
        EmployeeResponse employeeResponse=convertEmployeeResponse(employee);
        log.debug("[{}] [updateEmployee]->response:{} {}",this.getClass().getSimpleName(),employeeId,request);
        return InternalApiResponse.<EmployeeResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language
                                , FriendlyMessageCodeImpl.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language
                                , FriendlyMessageCodeImpl.EMPLOYEE_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(employeeResponse)
                .build();
    }

    @Operation(description = "This endpoint retrieves all employee records.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/employees")
    public InternalApiResponse<List<EmployeeResponse>> getEmployees(@PathVariable("language")Language language){
        log.debug("[{}] [getEmployees]",this.getClass().getSimpleName());
        List<Employee> employees=employeeService.getEmployees(language);
        List<EmployeeResponse> employeeResponses=convertEmployeeResponseList(employees);
        log.debug("[{}] [getEmployees]->response:{}",this.getClass().getSimpleName(),employeeResponses);
        return InternalApiResponse.<List<EmployeeResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(employeeResponses)
                .build();
    }

    @Operation(description = "This endpoint deletes an employee record")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/delete/{employeeId}")
    public InternalApiResponse<EmployeeResponse> deleteEmployee(@PathVariable("language")Language language,
                                                                @PathVariable("employeeId")Long employeeId){
        log.debug("[{}] [deleteEmployee]->request:{}",this.getClass().getSimpleName(),employeeId);
        Employee employee=employeeService.deleteEmployeeById(language,employeeId);
        EmployeeResponse employeeResponse=convertEmployeeResponse(employee);
        log.debug("[{}] [deleteEmployee]->response:{}",this.getClass().getSimpleName(),employeeResponse);
        return InternalApiResponse.<EmployeeResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language
                                , FriendlyMessageCodeImpl.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language
                                , FriendlyMessageCodeImpl.EMPLOYEE_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(employeeResponse)
                .build();
    }





    private static EmployeeResponse convertEmployeeResponse(Employee employee){
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .position(employee.getPosition())
                .department(employee.getDepartment())
                .endDate(employee.getEndDate().getTime())
                .salary(employee.getSalary())
                .startDate(employee.getStartDate().getTime())
                .build();
    }

    private List<EmployeeResponse> convertEmployeeResponseList(List<Employee> employeeList){
       return employeeList.stream()
                .map(args->EmployeeResponse.builder()
                        .employeeId(args.getEmployeeId())
                        .firstName(args.getFirstName())
                        .lastName(args.getLastName())
                        .email(args.getEmail())
                        .position(args.getPosition())
                        .department(args.getDepartment())
                        .salary(args.getSalary())
                        .endDate(args.getEndDate().getTime())
                        .startDate(args.getStartDate().getTime())
                        .build())
                .toList();
    }
}
