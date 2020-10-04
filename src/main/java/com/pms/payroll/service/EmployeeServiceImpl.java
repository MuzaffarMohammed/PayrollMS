package com.pms.payroll.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.payroll.config.ContextProvider;
import com.pms.payroll.controller.EmpJpaController;
import com.pms.payroll.model.PmsEmp;
import com.pms.payroll.util.Constants;

/**
 * @author Muzaffar Mohammed, +91 9951204368
 */
@Service(value = "employeeServiceImpl")
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {

	private EmpJpaController empJpaController = (EmpJpaController) ContextProvider.getBean("empJpaControllerImpl");

	private Cookie session;

	@Override
	public List<PmsEmp> findAllEmployees() {
		List<PmsEmp> emps = new ArrayList<>();
		try {
			emps = empJpaController.findPmsEmpEntities();
			if (emps != null) {
				for (PmsEmp emp : emps) {
					emp.setFinalSalary(emp.getAllowance() + emp.getSalary());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return emps;
	}

	@Override
	public PmsEmp getEmployeeById(Integer id) {
		PmsEmp pmsEmp = empJpaController.getPmsEmpById(id);
		pmsEmp.setFinalSalary(pmsEmp.getAllowance() + pmsEmp.getSalary());
		pmsEmp.setTds(calculateTds(pmsEmp.getFinalSalary(), pmsEmp.getAllowance()));
		return pmsEmp;
	}

	private Integer calculateTds(Integer finalSalary, Integer allowance) {
		Integer tdsDeducted = 0;
		try {
			int annualSalary = finalSalary * 12;

			if (annualSalary > 500000) {
				tdsDeducted = (int) (((double) 2 / 100) * (finalSalary - allowance));
				tdsDeducted = tdsDeducted + 200;
			} else {
				tdsDeducted = 200;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return tdsDeducted;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveEmployee(PmsEmp employee) {
		PmsEmp existPmsEmp = null;
		try {

			if (employee.getGrade() != null && !employee.getGrade().trim().equals("")) {
				Map<String, Integer> map = Constants.getAllowanceMap();
				if (map.get(employee.getGrade()) != null) {
					employee.setAllowance(map.get(employee.getGrade()));
				} else {
					System.out.println("Invalid Grade entered! Please enter among these {A, B ,C, D}.");
				}
			} else {
				System.out.println("Grade is missing...");
				return;
			}

			if (employee.getEmail_Id() != null) {
				if (employee.getEmail_Id().contains("@")) {
					employee.setUser(employee.getEmail_Id().split("@")[0]);
					employee.setPwd("12345");
				} else {
					System.out.println("Please enter correct Email Id!");
				}
			} else {
				System.out.println("Email Id is missing...");
				return;
			}

			if (employee.getPmsEmpPK() != null && employee.getPmsEmpPK().getId() != null) {
				existPmsEmp = getEmployeeById(employee.getPmsEmpPK().getId());
			}
			if (existPmsEmp != null) {
				existPmsEmp.setDesignation(employee.getDesignation());
				existPmsEmp.setDoj(employee.getDoj());
				existPmsEmp.setEmail_Id(employee.getEmail_Id());
				existPmsEmp.setGrade(employee.getGrade());
				existPmsEmp.setAllowance(employee.getAllowance());
				existPmsEmp.setSalary(employee.getSalary());
				empJpaController.edit(existPmsEmp);
			} else {
				empJpaController.create(employee);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteEmployee(Integer id) {
		try {
			empJpaController.deletePmsEmpById(id);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void editEmployee(PmsEmp pmsEmp) {
		try {
			empJpaController.edit(pmsEmp);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String login(PmsEmp employee) {
		String isAdmin = "";
		String userId = "";
		try {
			if (employee.getUser() != null && employee.getPwd() != null) {
				PmsEmp pmsEmp = empJpaController.checkUser(employee.getUser(), employee.getPwd());
				if (pmsEmp != null) {
					userId = pmsEmp.getPmsEmpPK().getId() + "";
					isAdmin = Constants.USER;
					if (pmsEmp.getUser().equals("admin") && pmsEmp.getPwd().equals("admin123")) {
						isAdmin = Constants.ADMIN + "";
					}
					// Adding cookie to maintain session.
					addCookie(Constants.SESSION, userId);
					if (isAdmin.equals(Constants.USER)) {
						// return "employee/"+userId;
						return "home";
					} else if (isAdmin.equals(Constants.ADMIN + "")) {
						return "employees";
					}
				} else {
					System.out.println("User not found!");
				}
			} else {
				System.out.println("Please enter user and password...");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return isAdmin;
	}

	public Cookie addCookie(String cookieName, String value) {
		session = new Cookie(cookieName, value);
		session.setMaxAge(600);
		return session;
	}

	public String isLoggedIn(HttpServletRequest request) {
		String loogedIn = null;
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(Constants.SESSION)) {
						loogedIn = cookie.getValue() + "";
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return loogedIn;
	}

	@Override
	@Transactional(readOnly = false)
	public String applyLeaves(Integer userId, String toApplyLeaves) {
		String redirect = "pms";
		try {
			PmsEmp pmsEmp = getEmployeeById(userId);
			if (pmsEmp != null) {
				pmsEmp.setLeaves(toApplyLeaves);
				empJpaController.edit(pmsEmp);
			}else {
				redirect = "error";	
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return redirect;
	}
	
	@Override
	@Transactional(readOnly = false)
	public String saveMyTravel(PmsEmp pmsEmp) {
		String redirect = "mytravel";
		try {
			if (pmsEmp != null && pmsEmp.getPmsEmpPK()!= null && pmsEmp.getPmsEmpPK().getId() != null) {
				empJpaController.edit(pmsEmp);
			}else {
				redirect = "redirect:/error";	
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return redirect;
	}

	/**
	 * @return the session
	 */
	public Cookie getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Cookie session) {
		this.session = session;
	}

}
