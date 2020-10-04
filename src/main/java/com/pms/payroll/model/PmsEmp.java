package com.pms.payroll.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */

/**
 * The persistent class for the pms_emp database table.
 * 
 */
@Entity
@Table(name="pms_emp")
public class PmsEmp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PmsEmpPK pmsEmpPK;
	private Integer allowance;
	private String designation;
	private String doj;
	private String email_Id;
	private String grade;
	private Integer salary;
	private String user;
	private String pwd;
	private String leaves;
	private String my_Travel;
	
	@Transient
	private Integer finalSalary;
	
	@Transient
	private Integer tds;



	public PmsEmp() {
	}

	public PmsEmpPK getPmsEmpPK() {
		return this.pmsEmpPK;
	}

	public void setPmsEmpPK(PmsEmpPK id) {
		this.pmsEmpPK = id;
	}

	public Integer getAllowance() {
		return this.allowance;
	}

	public void setAllowance(Integer allowance) {
		this.allowance = allowance;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDoj() {
		return this.doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getEmail_Id() {
		return this.email_Id;
	}

	public void setEmail_Id(String email_Id) {
		this.email_Id = email_Id;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getSalary() {
		return this.salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the finalSalary
	 */
	public Integer getFinalSalary() {
		return finalSalary;
	}

	/**
	 * @param finalSalary the finalSalary to set
	 */
	public void setFinalSalary(Integer finalSalary) {
		this.finalSalary = finalSalary;
	}

	/**
	 * @return the leaves
	 */
	public String getLeaves() {
		return leaves;
	}

	/**
	 * @return the tds
	 */
	public Integer getTds() {
		return tds;
	}

	/**
	 * @param leaves the leaves to set
	 */
	public void setLeaves(String leaves) {
		this.leaves = leaves;
	}

	/**
	 * @param tds the tds to set
	 */
	public void setTds(Integer tds) {
		this.tds = tds;
	}

	/**
	 * @return the my_Travel
	 */
	public String getMy_Travel() {
		return my_Travel;
	}

	/**
	 * @param my_Travel the my_Travel to set
	 */
	public void setMy_Travel(String my_Travel) {
		this.my_Travel = my_Travel;
	}

}