package com.CRM.exception;

import org.springframework.context.annotation.Configuration;


public class DuplicateResourceException extends RuntimeException{

    public DuplicateResourceException(
            String message) {

        super(message);
    }
}
