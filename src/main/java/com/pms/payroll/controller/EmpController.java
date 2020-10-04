package com.pms.payroll.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pms.payroll.config.ContextProvider;
import com.pms.payroll.controller.impl.exceptions.NonexistentEntityException;
import com.pms.payroll.model.PmsEmp;
import com.pms.payroll.service.EmployeeService;
import com.pms.payroll.util.Constants;
/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
@Controller
public class EmpController extends WebMvcConfigurerAdapter {

	// setup validation in controller
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/employee").setViewName("employee");
	}

	// injecting EmployeeService
	private EmployeeService employeeService = (EmployeeService) ContextProvider.getBean("employeeServiceImpl");

	@RequestMapping("pms")
	public String login(Model model, HttpServletRequest request) {

		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			if (userId.equals(Constants.ADMIN + "")) {
				return "redirect:/employees";
			} else {
				return "home";
			}
		} else {
			model.addAttribute("employee", new PmsEmp());
			return "login";
		}
	}
	
	@RequestMapping("ems")
	public String ems(Model model, HttpServletRequest request) {

		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
				return "redirect:/employee/"+userId;
		} else {
			model.addAttribute("employee", new PmsEmp());
			return "login";
		}
	}
	
	@RequestMapping("lms")
	public String lms(Model model, HttpServletRequest request) {

		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			PmsEmp pmsEmp = employeeService.getEmployeeById(Integer.parseInt(userId));
			if (pmsEmp != null) {
				model.addAttribute("employee", pmsEmp);
				return "leavems";
			}else {
				return "redirect:/error";
			}
		} else {
			model.addAttribute("employee", new PmsEmp());
			return "login";
		}
	}
	
	@RequestMapping("/apply/leaves")
	public String applyLeaves(@Valid PmsEmp employee, Model model, HttpServletRequest request) {
		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			String redirect = employeeService.applyLeaves(Integer.parseInt(userId), employee.getLeaves());
			return "redirect:/"+redirect;
		} else {
			model.addAttribute("employee", new PmsEmp());
			return "login";
		}
	}

	
	@RequestMapping("mts")
	public String myTravel(Model model, HttpServletRequest request) {

		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			PmsEmp pmsEmp = employeeService.getEmployeeById(Integer.parseInt(userId));
			if (pmsEmp != null) {
				model.addAttribute("employee", pmsEmp);
				return "mytravel";
			}else {
				return "redirect:/error";
			}
		} else {
			model.addAttribute("employee", new PmsEmp());
			return "login";
		}
	}
	
	@RequestMapping("/mytravel/save")
	public String saveMyTravel(@Valid PmsEmp employee, Model model, HttpServletRequest request) {
		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			
			PmsEmp pmsEmp = employeeService.getEmployeeById(Integer.parseInt(userId));
			if (pmsEmp != null) {
				model.addAttribute("employee", pmsEmp);
				pmsEmp.setMy_Travel(employee.getMy_Travel().replaceAll("LatLng\\(", "{").replaceAll("\\)", "}"));
				return employeeService.saveMyTravel(pmsEmp);
			}else {
				return "redirect:/error";
			}
		} else {
			model.addAttribute("employee", new PmsEmp());
			return "login";
		}
	}
	
	/**
	 * route to login screen
	 * 
	 * @param model mapped to login screen
	 * @return the accessUser or employees html
	 */
	@RequestMapping("authenticate")
	public String authenticate(@Valid PmsEmp employee, BindingResult bindingResult, HttpServletResponse response) {
		if (bindingResult.hasErrors()) {
			return "redirect:/error";
		} else {
			String url = employeeService.login(employee);
			if (employeeService.getSession() != null) {
				response.addCookie(employeeService.getSession());
				return url.equals("home") ? "home" : "redirect:/" + url;
			} else {
				return "redirect:/error";
			}
		}
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constants.SESSION)) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					return "redirect:/pms";
				}
			}
		}
		return "redirect:/pms";
	}

	/**
	 * route to create a new employee
	 * 
	 * @param model mapped to single employee
	 * @return the employeeform html
	 */
	@RequestMapping("employee/new")
	public String newEmployee(Model model, HttpServletRequest request) {
		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			model.addAttribute("employee", new PmsEmp());
			return "employeeform";
		} else {
			return "home";
		}
	}

	/**
	 * map Post request and /employee url to controller method
	 * 
	 * @param employee      object
	 * @param bindingResult checks the fields for validation
	 * @return the employeeform html if there are errors or else return the employee
	 *         detail page
	 */
	@PostMapping("employee")
	public String saveEmployee(@Valid PmsEmp employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "employeeform";
		}
		employeeService.saveEmployee(employee);
		return "redirect:/employees";
	}

	/**
	 * route to get a single employee page
	 * 
	 * @param id    grab the employee id
	 * @param model mapped to single employee
	 * @return single employee page
	 */
	@RequestMapping("employee/{id}")
	public String showEmployee(@PathVariable Integer id, Model model, HttpServletRequest request) {
		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			PmsEmp pmsEmp = employeeService.getEmployeeById(id);
			if (pmsEmp != null) {
				model.addAttribute("employee", pmsEmp);
				return "employeeshow";
			} else {
				return "redirect:/error";
			}
		} else {
			return "home";
		}
	}

	/**
	 * map Get request and /employees url to controller method
	 * 
	 * @param model mapped to all Employees
	 * @return list of employees in employees.html
	 */
	@GetMapping("/employees")
	public String list(Model model, HttpServletRequest request) {
		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			if (employeeService.findAllEmployees() != null) {
				model.addAttribute("employees", employeeService.findAllEmployees());
				return "employees";
			} else {
				return "redirect:/error";
			}
		} else {
			return "home";
		}

	}

	/**
	 * Route to edit an employee
	 * 
	 * @param id    grab the employee id
	 * @param model mapped to a single employee
	 * @return employeeform.html page
	 */
	@RequestMapping("employee/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, HttpServletRequest request) {
		String userId = employeeService.isLoggedIn(request);
		if (userId != null) {
			PmsEmp pmsEmp = employeeService.getEmployeeById(id);
			if (pmsEmp != null) {
				model.addAttribute("employee", pmsEmp);
				return "employeeform";
			} else {
				return "redirect:/error";
			}
		} else {
			return "home";
		}
	}

	/**
	 * Route to delete an employee
	 * 
	 * @param id grab the employee id
	 * @return redirect to list of employees after deletion
	 * @throws NonexistentEntityException
	 */
	@RequestMapping("employee/delete/{id}")
	public String delete(@PathVariable Integer id) throws NonexistentEntityException {
		employeeService.deleteEmployee(id);
		return "redirect:/employees";
	}

}
