package com.herrera.emsbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created on 11/13/2025 by Jesse H.
 * <p>
 * Package: com.herrera.emsbackend.model; Class: Employee
 * <p>
 * JPA Entity Class for Employee
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    /**
     * Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Employee First name
     */
    @Column(name = "first_name")
    private String firstName;
    /**
     * Employee Last name
     */
    @Column(name = "last_name")
    private String lastName;
    /**
     * Employee Email
     */
    @Column(name = "email_id", nullable = false, unique = true)
    private String email;
    /**
     * Employee Phone Number
     */
    @Column(name = "phone_id")
    private String phone;
} // Employee Class
