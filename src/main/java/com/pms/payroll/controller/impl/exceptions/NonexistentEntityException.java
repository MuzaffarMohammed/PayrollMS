package com.pms.payroll.controller.impl.exceptions;
/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
public class NonexistentEntityException extends Exception {
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
