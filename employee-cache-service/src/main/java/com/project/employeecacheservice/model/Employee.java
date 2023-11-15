package com.project.employeecacheservice.model;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Employee")
public class Employee implements Serializable {

    @Id
    private Long employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private Double salary;

    private String department;

    private String position;

    private Long startDate;

    private Long endDate;

    private boolean isActive;

    private boolean deleted;










}

