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
        return new EmployeeDTO(id, firstName, "Doe", email, "555-555-5555", "Engineering", 65000);
    } // createTestEmployee()
} // EmployeeControllerTest Class
