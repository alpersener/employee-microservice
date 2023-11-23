package com.project.employeeservice.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.employeeservice.user.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
    private String password;
}

