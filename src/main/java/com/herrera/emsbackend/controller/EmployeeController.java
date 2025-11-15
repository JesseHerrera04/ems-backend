package com.herrera.emsbackend.controller;

import com.herrera.emsbackend.dto.EmployeeDTO;
import com.herrera.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * This method gets an Employee by ID
     *
     * @param empId - Employee ID
     * @return ResponseEntity with status 200 and the EmployeeDTO of the found Employee
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long empId) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(empId);
        return ResponseEntity.ok(employeeDTO);
    } // getEmployeeById()

    /**
     * This method gets all Employees
     *
     * @return ResponseEntity with status 200 and a List of EmployeeDTOs
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    } // getAllEmployees()

    /**
     * This method updates an Employee
     *
     * @param empId           - Employee ID to update
     * @param updatedEmployee - EmployeeDTO with updated data
     * @return ResponseEntity with status 200 and the EmployeeDTO of the updated Employee
     */
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable("id") Long empId,
            @RequestBody EmployeeDTO updatedEmployee
    ) {
        EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(empId, updatedEmployee);
        return ResponseEntity.ok(updatedEmployeeDTO);
    } // updateEmployee()

    /**
     * This method deletes an Employee by ID
     *
     * @param empId - Employee ID to delete
     * @return ResponseEntity with status 200 and a message indicating success
     */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long empId) {
        employeeService.deleteEmployeeById(empId);
        return ResponseEntity.ok("Employee Successfully Deleted.");
    } // deleteEmployeeById()
} // EmployeeController Class
