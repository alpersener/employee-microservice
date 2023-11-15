package com.project.employeeservice.request;

import lombok.Data;

@Data
public class EmployeeUpdateRequest {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private String department;
    private String position;

}
