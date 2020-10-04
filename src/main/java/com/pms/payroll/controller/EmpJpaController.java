/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pms.payroll.controller;

import java.util.List;

import com.pms.payroll.controller.impl.exceptions.NonexistentEntityException;
import com.pms.payroll.model.PmsEmp;
import com.pms.payroll.model.PmsEmpPK;

/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
public interface EmpJpaController{

    public void create(PmsEmp pmsEmp) throws Exception;
    
    public void edit(PmsEmp pmsEmp) throws Exception ;
    
    public void destroy(PmsEmpPK id) throws NonexistentEntityException ;

    public List<PmsEmp> findPmsEmpEntities() ;

    public List<PmsEmp> findPmsEmpEntities(int maxResults, int firstResult) ;

    public List<PmsEmp> findPmsEmpEntities(boolean all, int maxResults, int firstResult) ;

    public PmsEmp findPmsEmp(PmsEmpPK id);
    
    public PmsEmp getPmsEmpById(Integer id);

    public void deletePmsEmpById(Integer id);
    
    public int getPmsEmpCount();
    
    public PmsEmp checkUser(String user, String pwd);
}
