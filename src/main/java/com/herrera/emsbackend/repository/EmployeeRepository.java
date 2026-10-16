package com.herrera.emsbackend.repository;

import com.herrera.emsbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * Created on 11/15/2025 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.repository; Class: EmployeeRepository
 * <p>
 * JPA Repository Interface for Employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * This method finds and retrieves all employees from a specified department
     * 
     * @param department - the department to be searched
     * @return List of employees belonging to specified apartment
     */
    List<Employee> findByDepartmentIgnoreCase(String department);
}
