package com.herrera.emsbackend.repository;

import com.herrera.emsbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 11/15/2025 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.repository; Class: EmployeeRepository
 * <p>
 * JPA Repository Interface for Employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
