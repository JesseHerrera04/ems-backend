package com.herrera.emsbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created on 11/15/2025 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.dto; Class: EmployeeDTO
 * <p>
 * DTO for Employee Class
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    // Fields
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(
            regexp = "^\\+1[2-9]\\d{2}[2-9]\\d{6}$",
            message = "must be a valid US phone number in E.164 format"
    )
    private String phone;
    private String department;
    private Integer salary;
} // EmployeeDTO Class
