/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pms.payroll.config;

import java.util.Locale;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
@Configuration
public class ContextProvider implements ApplicationContextAware {


    @Autowired
    public static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        // TODO Auto-generated method stub
        ContextProvider.context = context;
    }

    /**
     *
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        System.out.println("context = "+context);
        return context.getBean(beanName);
    }

    /**
     *
     * @param beanName
     * @return
     */
    public static String getMessage(String messageKey) {
        return context.getMessage(messageKey, new Object[]{}, Locale.US);
    }

}
