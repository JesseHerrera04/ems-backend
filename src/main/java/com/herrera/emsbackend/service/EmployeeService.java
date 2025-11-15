package com.herrera.emsbackend.service;

import com.herrera.emsbackend.dto.EmployeeDTO;

/**
 * Created on 11/15/2025 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.service; Class: EmployeeService
 * <p>
 * Interface for Employee Service Business Logic
 */
public interface EmployeeService {
    /**
     * This method creates a new Employee
     *
     * @param employeeDTO - EmployeeDTO
     * @return EmployeeDTO - EmployeeDTO of the entity created
     */
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
} // EmployeeService Interface
