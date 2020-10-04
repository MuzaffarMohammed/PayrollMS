package com.pms.payroll.controller.impl.exceptions;
/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
public class PreexistingEntityException extends Exception {
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
