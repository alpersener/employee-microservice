package com.project.employeecacheservice.feign.employee;

import com.project.employeecacheservice.enums.Language;
import com.project.employeecacheservice.response.EmployeeResponse;
import com.project.employeecacheservice.response.InternalApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.employee-service.name}")
public interface EmployeeServiceFeignClient {

    @GetMapping("/api/v1/employee/{language}/get/{employeeId}")
    InternalApiResponse<EmployeeResponse> getEmployee(@PathVariable("language")Language language,
                                                      @PathVariable("employeeId")Long employeeId);
}
