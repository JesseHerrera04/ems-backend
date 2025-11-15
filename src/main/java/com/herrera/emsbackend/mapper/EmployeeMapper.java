package com.herrera.emsbackend.mapper;

import com.herrera.emsbackend.dto.EmployeeDTO;
import com.herrera.emsbackend.model.Employee;

/**
 * Created on 11/15/2025 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.mapper; Class: EmployeeMapper
 * <p>
 * Mapper for Employee Data
 */
public class EmployeeMapper {
    /**
     * This method maps an Employee Entity/POJO to an EmployeeDTO
     *
     * @param employee - Employee Entity/POJO
     * @return EmployeeDTO constructed from Employee data
     */
    public static EmployeeDTO mapToEmployeeDTO(Employee employee) {
        // Return new DTO with all fields from Employee
        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhone()
        );
    } // mapToEmployeeDTO()

    /**
     * This method maps an EmployeeDTO to an Employee Entity/POJO
     *
     * @param employeeDTO - EmployeeDTO
     * @return Employee constructed from EmployeeDTO data
     */
    public static Employee mapToEmployee(EmployeeDTO employeeDTO) {
        return new Employee(
                employeeDTO.getId(),
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getEmail(),
                employeeDTO.getPhone()
        );
    } // mapToEmployee()
} // EmployeeMapper Class
