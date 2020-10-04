package com.pms.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */

@SpringBootApplication
public class PayrollMSApplication {

	private static final Logger log = LoggerFactory.getLogger(PayrollMSApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PayrollMSApplication.class, args);
		log.debug("Testing... Playroll...");
	}
}
