package com.herrera.emsbackend.service.impl;

import com.herrera.emsbackend.dto.EmployeeDTO;
import com.herrera.emsbackend.exception.ResourceNotFoundException;
import com.herrera.emsbackend.mapper.EmployeeMapper;
import com.herrera.emsbackend.model.Employee;
import com.herrera.emsbackend.repository.EmployeeRepository;
import com.herrera.emsbackend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    // Fields
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        // Create employee and map dto to it
        Employee employee = EmployeeMapper.mapToEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee); // Save employee to database
        log.info("Employee created with id: {}", savedEmployee.getId());
        return EmployeeMapper.mapToEmployeeDTO(savedEmployee); // Return saved employee dto
    } // createEmployee()

    @Override
    public EmployeeDTO getEmployeeById(Long empId) {
        log.info("Getting employee by id: {}", empId);
        // Get employee by id and map to dto. Throw exception if not found.
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found for this id: " + empId)
                );
        log.info("Employee #{} found!", empId);
        return EmployeeMapper.mapToEmployeeDTO(employee);
    } // getEmployeeById()

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Getting all employees");
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
    } // getAllEmployees()

    @Override 
    public List<EmployeeDTO> getAllEmployeesByDept(String deptName) {
        List<Employee> employees = employeeRepository.findByDepartmentIgnoreCase(deptName);

        // Map each employee to dto and return list of employees
        return employees.stream().map(EmployeeMapper::mapToEmployeeDTO).toList();
    } // getAllEmployeesByDept()

    @Override
    public EmployeeDTO updateEmployee(Long empId, EmployeeDTO updatedEmployee) {
        log.info("Updating employee with id: {}", empId);
        // Get employee by id and map to dto. Throw exception if not found.
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found for this id: " + empId)
                );

        // Update employee fields with updatedEmployee fields
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhone(updatedEmployee.getPhone());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setSalary(updatedEmployee.getSalary());

        // Save updated employee
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee #{} updated!", empId);

        // Return updated employee dto
        return EmployeeMapper.mapToEmployeeDTO(savedEmployee);
    } // updateEmployee()

    @Override
    public void deleteEmployeeById(Long empId) {
        log.info("Deleting employee with id: {}", empId);
        // Find the employee by id first (throw exception if not found)
        employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found for this id: " + empId)
                );

        // Delete employee
        employeeRepository.deleteById(empId);
        log.info("Employee deleted for id: {}", empId);
    } // deleteEmployeeById()
} // EmployeeServiceImpl Class
