package com.herrera.emsbackend.repository;

import com.herrera.emsbackend.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=validate"
})
@AutoConfigureTestDatabase(replace = Replace.ANY)
/**
 * Created on 11/14/2026 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.repository (Test); Class: EmployeeRepositoryTest
 * <p>
 * DataJPATest Class for Employee Repository
 */
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository; // The repository used for tests

    // Test employees
    private Employee john;
    private Employee jane;
    private Employee alex;

    @BeforeEach
    void setUp() {
        // Set up test employees to use for testing
        john = createTestEmployee("John", "Engineering");
        jane = createTestEmployee("Jane", "engineering");
        alex = createTestEmployee("Alex", "Human Resources");

        // Save employees to database
        employeeRepository.save(john);
        employeeRepository.save(jane);
        employeeRepository.save(alex);
    }

    // Test the ignore case findbydepartment to ensure it can ignore case and ONLY return employees from the given department
    @Test
    void findByDepartmentIgnoreCaseReturnsAllCaseVariantsOnly() {
        // Given: list of employees
        // When: calling repository with department name using any case
        List<Employee> matches = employeeRepository.findByDepartmentIgnoreCase("ENGINEERING");

        // Then: return all employees in that department regardless of case and assert that the results are valid
        assertThat(matches)
                .extracting(Employee::getFirstName)
                .containsExactlyInAnyOrder("John", "Jane");
    }

    // Helper function made to make test entity creation easier (no null/missing fields)
    private Employee createTestEmployee(String firstName, String department) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName("Doe");
        employee.setEmail(firstName.toLowerCase() + "@example.com");
        employee.setPhone("+14155552671");
        employee.setDepartment(department);
        employee.setSalary(65000);
        return employee;
    } // createTestEmployee()
} // EmployeeRepositoryTest Class
