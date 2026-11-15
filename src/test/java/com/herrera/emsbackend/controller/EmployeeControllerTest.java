package com.herrera.emsbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.herrera.emsbackend.dto.EmployeeDTO;
import com.herrera.emsbackend.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
/**
 * Created on 11/14/2026 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.controller (Test); Class: EmployeeControllerTest
 * <p>
 * WebMvcTest Class for Employee Controller
 */
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    // Test that employee creation successfully returns employee
    @Test
    void testCreateEmployeeReturnsCreatedEmployee() throws Exception {
        // Given: employee and DTO being created
        EmployeeDTO request = createTestEmployee(null, "John", "john@example.com");
        EmployeeDTO saved = createTestEmployee(1L, "John", "john@example.com");

        // When: employee service is called to create employee in DB
        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(saved);

        // Then: upon invoking POST endpoint, 201 status should be returned alongside JSON
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        // Verify that service createEmployee was called and returned a DTO
        verify(employeeService).createEmployee(any(EmployeeDTO.class));
    } // testCreateEmployeeReturnsCreatedEmployee()

    // Test creating an employee but failing due to bad email
    @Test
    void testCreateEmployeeRejectsInvalidEmail() throws Exception {
        // Given: employee with invalid email
        EmployeeDTO request = createTestEmployee(null, "John", "not-an-email");

        // When: JSON is sent to controller endpoint for adding employee
        // Then: Perform validation and reject the bad data and return bad request status code
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        // Employee service should not be called since bad requests should be stopped before reaching service layer
        verifyNoInteractions(employeeService);
    } // testCreateEmployeeRejectsInvalidEmail()

    // Tests that employee creation fails if invalid phone number length
    @Test
    void testCreateEmployeeRejectsPhoneWithInvalidLength() throws Exception {
        // Given: employee with phone number with invalid length (one digit short in this scenario)
        EmployeeDTO request = createTestEmployee(null, "John", "john@example.com");
        request.setPhone("+1415555267");

        // When: JSON sent to API endpoint
        // Then: Reject bad data and return error status
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        // Verify that service layer was never interacted with (only ever proceed if data is validated)
        verifyNoInteractions(employeeService);
    } // testCreateEmployeeRejectsPhoneWithInvalidLength()

    // Tests employee creation failing if phone number is not in the right format 
    @Test
    void testCreateEmployeeRejectsNonE164PhoneFormat() throws Exception {
        // Given: employee with phone number in wrong format (E164 enforced for DB storage)
        EmployeeDTO request = createTestEmployee(null, "John", "john@example.com");
        request.setPhone("415-555-2671");

        // When: JSON is sent to API endpoint
        // Then: Perform validation check, reject and return 400 status code
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        // Verify that service layer was never interacted with (only ever proceed if data is validated)
        verifyNoInteractions(employeeService);
    } // testCreateEmployeeRejectsNonE164PhoneFormat()

    // Tests employee creation still works if department and salary not available (usually due to new hire)
    @Test
    void testCreateEmployeeAllowsMissingDepartmentAndSalary() throws Exception {
        // Given: Employee without dept or salary data
        EmployeeDTO request = createTestEmployee(null, "John", "john@example.com");
        request.setDepartment(null);
        request.setSalary(null);

        EmployeeDTO saved = createTestEmployee(1L, "John", "john@example.com");
        saved.setDepartment(null);
        saved.setSalary(null);

        // When: creating employee and saving to database
        when(employeeService.createEmployee(any(EmployeeDTO.class))).thenReturn(saved);

        // Then: return 201 status like with any other creation event
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Verify that the employee service was called and that the department and salary fields were null
        verify(employeeService).createEmployee(argThat(employee ->
                employee.getDepartment() == null && employee.getSalary() == null
        ));
    } // testCreateEmployeeAllowsMissingDepartmentAndSalary()

    // Test that employee retrieval by ID is successful
    @Test
    void testGetEmployeeByIdReturnsEmployee() throws Exception {
        // Given: Employee to find, and ID provided
        EmployeeDTO found = createTestEmployee(7L, "Jane", "jane@example.com");

        // When: employee service called to find by ID
        when(employeeService.getEmployeeById(7L)).thenReturn(found);

        // Then: Endpoint should return 200 status as well as JSON of employee data
        mockMvc.perform(get("/api/employees/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));

        // Verify that the service was called with the correct function
        verify(employeeService).getEmployeeById(7L);
    } // testGetEmployeeByIdReturnsEmployee()

    // Test that all employees are returned successfully
    @Test
    void testGetAllEmployeesReturnsEmployeeList() throws Exception {
        // Given: employees in list
        // When: service is called for retrieval of all employees
        when(employeeService.getAllEmployees()).thenReturn(List.of(
                createTestEmployee(1L, "John", "john@example.com"),
                createTestEmployee(2L, "Jane", "jane@example.com")
        ));

        // Then: Endpoint should return 200 status and full JSON list
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));

        // Verify that the service was run        
        verify(employeeService).getAllEmployees();
    } // testGetEmployeeByIdReturnsEmployee()

    // Test that updates are successful and return the employee
    @Test
    void testUpdateEmployeeReturnsUpdatedEmployee() throws Exception {
        // Give: employee data to be updated
        EmployeeDTO request = createTestEmployee(null, "Janet", "janet@example.com");
        EmployeeDTO updated = createTestEmployee(7L, "Janet", "janet@example.com");

        // When: employee service update function called with DTO with new data and specified ID
        when(employeeService.updateEmployee(eq(7L), any(EmployeeDTO.class))).thenReturn(updated);

        // Then: Return 200 status and updated employee data
        mockMvc.perform(put("/api/employees/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.firstName").value("Janet"))
                .andExpect(jsonPath("$.email").value("janet@example.com"));

        // Verify that service was called and that data matches        
        verify(employeeService).updateEmployee(eq(7L), argThat(employee ->
                "Janet".equals(employee.getFirstName())
                        && "janet@example.com".equals(employee.getEmail())
        ));
    } // testUpdateEmployeeReturnsUpdatedEmployee()

    // Test updating employee is rejected if name is blank
    @Test
    void testUpdateEmployeeRejectsBlankFirstName() throws Exception {
        // Given: Employee updated to have blank first name
        EmployeeDTO request = createTestEmployee(null, " ", "janet@example.com");

        // When: JSON data sent to endpoint to update employee
        // Then: perform data validation, reject update, and return 400 status code
        mockMvc.perform(put("/api/employees/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(employeeService);
    } // testUpdateEmployeeRejectsBlankFirstName()

    // Test employee deletion is successful and returns success message
    @Test
    void testDeleteEmployeeReturnsSuccessMessage() throws Exception {
        // Given: Employee ID marked for deletion
        // When: Service is called with ID to delete
        // Then: Employee is deleted and endpoint returns 200 status
        mockMvc.perform(delete("/api/employees/7"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee Successfully Deleted."));

        // Verify that the service was run        
        verify(employeeService).deleteEmployeeById(7L);
    } // testDeleteEmployeeReturnsSuccessMessage()

    // Helper function to prepopulate most of the test data when creating the employees
    private EmployeeDTO createTestEmployee(Long id, String firstName, String email) {
        return new EmployeeDTO(id, firstName, "Doe", email, "+14155552671", "Engineering", 65000);
    } // createTestEmployee()
} // EmployeeControllerTest Class
