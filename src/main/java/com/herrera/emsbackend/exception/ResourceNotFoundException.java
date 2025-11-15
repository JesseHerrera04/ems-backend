package com.herrera.emsbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created on 11/15/2025 by Jesse H
 * <p>
 * Package: com.herrera.emsbackend.exception; Class: ResourceNotFoundException
 * <p>
 * Custom Exception for Resource Not Found
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Requested Resource Not Found")
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor for ResourceNotFoundException Class
     *
     * @param message - Exception Message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    } // ResourceNotFoundException Constructor
} // ResourceNotFoundException Class
