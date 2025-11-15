package com.herrera.emsbackend.service.impl;

import com.herrera.emsbackend.dto.EmployeeDTO;
import com.herrera.emsbackend.exception.ResourceNotFoundException;
import com.herrera.emsbackend.mapper.EmployeeMapper;
import com.herrera.emsbackend.model.Employee;
import com.herrera.emsbackend.repository.EmployeeRepository;
import com.herrera.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 11/15/2025 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.service.impl; Class: EmployeeServiceImpl
 * <p>
 * Implementation for Employee Service Business Logic
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    // Fields
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // Create employee and map dto to it
        Employee employee = EmployeeMapper.mapToEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee); // Save employee to database
        return EmployeeMapper.mapToEmployeeDTO(savedEmployee); // Return saved employee dto
    } // createEmployee()

    @Override
    public EmployeeDTO getEmployeeById(Long empId) {
        // Get employee by id and map to dto. Throw exception if not found.
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found for this id: " + empId)
                );
        return EmployeeMapper.mapToEmployeeDTO(employee);
    } // getEmployeeById()

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        /*
         * Note: This is just a personal note for reference
         * Below (after commented code) is the Java 16+ version of this code:
         * return employees.stream()
         *                 .map((employee) -> EmployeeMapper.mapToEmployeeDTO(employee))
         *                 .collect(Collectors.toList())
         * Note: This list is mutable, but no reason to do so for a GET operation
         */
        // Map each employee to dto and return list (immutable)
        return employees.stream().map(EmployeeMapper::mapToEmployeeDTO).toList();
    }
}
