package com.herrera.emsbackend.service.impl;

import com.herrera.emsbackend.dto.EmployeeDTO;
import com.herrera.emsbackend.mapper.EmployeeMapper;
import com.herrera.emsbackend.model.Employee;
import com.herrera.emsbackend.repository.EmployeeRepository;
import com.herrera.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    }
}
