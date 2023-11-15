package com.project.employeeservice.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Double salary;
    private String department;
    private String position;
}
