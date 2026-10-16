package com.herrera.emsbackend.service;

import com.herrera.emsbackend.dto.EmployeeDTO;
import com.herrera.emsbackend.exception.ResourceNotFoundException;
import com.herrera.emsbackend.model.Employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.herrera.emsbackend.repository.EmployeeRepository;
import com.herrera.emsbackend.service.impl.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created on 10/15/202 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.service (Test); Class: EmployeeServiceImplTest
 * <p>
 * JUnit Tests Class for Employee Service
 */
public class EmployeeServiceImplTest {

    EmployeeServiceImpl employeeService;

    @Mock 
    EmployeeRepository employeeRepository;

    // Setup the mock repository and service
    @BeforeEach 
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    // Test employee creation
    @Test
    void testCreateEmployee() {
        // Given (Init EmployeeDTO)
        // New Employee data
        String fName = "John";
        String lName = "Doe";
        String email = "JohnDoe@test.com";
        String phone = "555-555-5555";
        String department = "Accounting";
        int salary = 65000;

        // Incoming DTO
        EmployeeDTO newEmployee = new EmployeeDTO();
        newEmployee.setFirstName(fName);
        newEmployee.setLastName(lName);
        newEmployee.setEmail(email);
        newEmployee.setPhone(phone);
        newEmployee.setDepartment(department);
        newEmployee.setSalary(salary);

        // Entity persisted to database
        Employee savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setFirstName(fName);
        savedEmployee.setLastName(lName);
        savedEmployee.setEmail(email);
        savedEmployee.setPhone(phone);
        savedEmployee.setDepartment(department);
        savedEmployee.setSalary(salary);

        // When (save is called)
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee); // Then (return saved employee)

        EmployeeDTO savedEmployeeDTO = employeeService.createEmployee(newEmployee); // Call service func and return DTO

        assertNotNull(savedEmployeeDTO); // Assert returned object exists
        verify(employeeRepository, times(1)).save(any(Employee.class)); // Verify that only 1 save executed
        assertEquals(1L, savedEmployeeDTO.getId()); // Assert ID is 1L
        assertEquals(fName, savedEmployeeDTO.getFirstName()); // Assert fname is the same
        assertEquals(lName, savedEmployeeDTO.getLastName()); // Assert lname is the same
        assertEquals(email, savedEmployeeDTO.getEmail()); // Assert email is the same
        assertEquals(phone, savedEmployeeDTO.getPhone()); // Assert phone is the same
        assertEquals(department, savedEmployeeDTO.getDepartment()); // Assert department is the same
        assertEquals(salary, savedEmployeeDTO.getSalary()); // Assert salary is the same
    } // testCreateEmployee()
    

    @Test 
    void testUpdateEmployee() {
        // Given (Init EmployeeDTO & Employee)
        // New Employee data
        long empId = 1L;
        String fName = "John";
        String lName = "Doe";
        String email = "JohnDoe@test.com";
        String phone = "555-555-5555";
        String department = "Accounting";
        int salary = 65000;
        int newSalary = 70000;

        // Incoming DTO
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setFirstName(fName);
        updatedEmployeeDTO.setLastName(lName);
        updatedEmployeeDTO.setEmail(email);
        updatedEmployeeDTO.setPhone(phone);
        updatedEmployeeDTO.setDepartment(department);
        updatedEmployeeDTO.setSalary(newSalary);

        // Entity persisted to database
        Employee savedEmployee = new Employee();
        savedEmployee.setId(empId);
        savedEmployee.setFirstName(fName);
        savedEmployee.setLastName(lName);
        savedEmployee.setEmail(email);
        savedEmployee.setPhone(phone);
        savedEmployee.setDepartment(department);
        savedEmployee.setSalary(salary);

        // New updated employee
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(empId);
        updatedEmployee.setFirstName(fName);
        updatedEmployee.setLastName(lName);
        updatedEmployee.setEmail(email);
        updatedEmployee.setPhone(phone);
        updatedEmployee.setDepartment(department);
        updatedEmployee.setSalary(newSalary);

        // When (find by ID and save)
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(savedEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
        // Then (return object and save updated object)

        EmployeeDTO returnedEmployeeDTO = employeeService.updateEmployee(empId, updatedEmployeeDTO); // Call service func

        assertNotNull(returnedEmployeeDTO);
        verify(employeeRepository, times(1)).findById(anyLong());
        verify(employeeRepository, times(1)).save(any(Employee.class));
        assertEquals(savedEmployee.getId(), returnedEmployeeDTO.getId()); // Assert ID is the same
        assertEquals(newSalary, returnedEmployeeDTO.getSalary()); // Assert salary is the new salary (should be different)
        assertEquals(fName, returnedEmployeeDTO.getFirstName()); // Assert fname is the same
        assertEquals(lName, returnedEmployeeDTO.getLastName()); // Assert lname is the same
        assertEquals(email, returnedEmployeeDTO.getEmail()); // Assert email is the same
        assertEquals(phone, returnedEmployeeDTO.getPhone()); // Assert phone is the same
        assertEquals(department, returnedEmployeeDTO.getDepartment()); // Assert department is the same
    } // testUpdateEmployee()

    // Test getting employee by ID
    @Test 
    void testGetEmployeeById() {
        // Given (Employee with ID 1)
        Employee foundEmployee = new Employee();
        foundEmployee.setId(1L);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(foundEmployee));

        EmployeeDTO returnedEmployeeDTO = employeeService.getEmployeeById(1L);

        assertNotNull(returnedEmployeeDTO);
        assertEquals(1L, returnedEmployeeDTO.getId());
        verify(employeeRepository, times(1)).findById(anyLong());
    } // testGetEmployeeById()

    // Test failing to get an employee by ID
    @Test 
    void testGetEmployeeByIdFail() {
        // Given (employee does not exist OR ID is wrong)
        long wrongId = 1L;

        // When (searching by ID)
        when(employeeRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Employee not found for this id: " + wrongId)); // Then return exception (nothing was found)

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(wrongId)); // Assert exception is thrown
    } // testGetEmployeeByIdFail()

    // Test getting all employees
    @Test 
    void testGetAllEmployees() {
        // Initialize test variables (Given)
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> testEmployees = new ArrayList<>();
        employee1.setId(1L);
        employee2.setId(2L);
        // Add to the list
        testEmployees.add(employee1);
        testEmployees.add(employee2);

        // Specify initial condition (When)
        // When repository called, return a list of employee objects
        when(employeeRepository.findAll()).thenReturn(testEmployees);  // Specify desired outcome (Then) - Employee List returned

        // Call the service as you normally would
        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size()); // Assert that ALL objects are present (2 have been made, 2 must be present)
        verify(employeeRepository, times(1)).findAll(); // Verify that only a single repository operation has been made
        verify(employeeRepository, never()).findById(anyLong()); // Verify that finding by ID has not been used
    } // testGetAllEmployees()

    // Test getting all employees by department
    @Test 
    void testGetAllEmployeesByDept() {
        // Initialize test variables (Given)
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        Employee employee3 = new Employee();
        List<Employee> testEmployees = new ArrayList<>();
        // Set properties
        employee1.setId(1L);
        employee2.setId(2L);
        employee3.setId(3L);
        // Test case ignore by using different casing
        employee1.setDepartment("Engineering");
        employee2.setDepartment("engineering");
        employee3.setDepartment("ENGINEERING");
        // Add to the list
        testEmployees.add(employee1);
        testEmployees.add(employee2);
        testEmployees.add(employee3);

        // When (finding employees in repository by dept.)
        when(employeeRepository.findByDepartmentIgnoreCase(anyString())).thenReturn(testEmployees); // Then (return all employees in Engineering)

        List<EmployeeDTO> engineeringEmployees = employeeService.getAllEmployeesByDept("Engineering"); // Call the service func and get the list

        assertEquals(3, engineeringEmployees.size()); // Assert that all 3 test objects are indeed present
        verify(employeeRepository, times(1)).findByDepartmentIgnoreCase(anyString()); // Verify that only one call to the repository was made
        verify(employeeRepository, never()).findAll(); // Verify that .findAll() was never called
        verify(employeeRepository, never()).findById(anyLong()); // Verify that .findById was never called
    } // testGetAllEmployeesByDept()

    // Test employee deletion by ID
    @Test 
    void testDeleteEmployeeById() {
        // Given (employee to delete)
        long empId = 1L;
        Employee deletedEmployee = new Employee();
        deletedEmployee.setId(empId);

        // When (locating employee to delete)
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(deletedEmployee));
        // Then (call deletion function)
        employeeService.deleteEmployeeById(empId); // Call the service function

        verify(employeeRepository, times(1)).deleteById(anyLong()); // Verify delete was called only once
    } // testDeleteEmployeeById()

    // Test employee deletion by ID fail
    @Test 
    void testDeleteEmployeeByIdFail() {
        // Given (no employee to delete found)
        long wrongId = 1L;

        // When (locating employee to delete)
        when(employeeRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Employee not found for this id: " + wrongId)); // Then return exception (nothing was found)

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployeeById(wrongId)); // Assert exception is thrown (deletion never called)
    } // testDeleteEmployeeById()
}
