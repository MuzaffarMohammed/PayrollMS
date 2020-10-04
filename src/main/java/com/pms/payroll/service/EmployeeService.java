package com.pms.payroll.service;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.pms.payroll.model.PmsEmp;
/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
public interface EmployeeService {

	public List<PmsEmp> findAllEmployees();
    
    public PmsEmp getEmployeeById(Integer id);
    
    public void saveEmployee(PmsEmp employee);
    
    public void editEmployee(PmsEmp pmsEmp);
    
    public void deleteEmployee(Integer id);
    
    public String login(PmsEmp employee);
    
    public Cookie getSession();
    
    public String isLoggedIn(HttpServletRequest request);
    
    public String applyLeaves(Integer userId, String toApplyLeaves) ;
    
    public String saveMyTravel(PmsEmp pmsEmp) ;
}
