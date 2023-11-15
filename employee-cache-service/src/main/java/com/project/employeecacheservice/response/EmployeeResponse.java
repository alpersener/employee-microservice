package com.project.employeecacheservice.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponse {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private String department;
    private String position;
    private Long startDate;
    private Long endDate;

}
