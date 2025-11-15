package com.herrera.emsbackend.controller;

import com.herrera.emsbackend.dto.EmployeeDTO;
import com.herrera.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 11/15/2025 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.controller; Class: EmployeeController
 * <p>
 * REST Controller for Employees
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    /**
     * This method creates a new Employee
     *
     * @param employeeDTO - The EmployeeDTO with data to create a new Employee
     * @return ResponseEntity with status 201 and the EmployeeDTO of the created Employee
     */
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO savedEmployeeDTO = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeDTO);

    } // createEmployee()

} // EmployeeController Class
