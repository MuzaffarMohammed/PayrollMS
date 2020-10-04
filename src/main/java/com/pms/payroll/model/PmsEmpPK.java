package com.pms.payroll.model;

import java.io.Serializable;
import javax.persistence.*;
/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
/**
 * The primary key class for the pms_emp database table.
 * 
 */
@Embeddable
public class PmsEmpPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	public PmsEmpPK() {
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PmsEmpPK)) {
			return false;
		}
		PmsEmpPK castOther = (PmsEmpPK)other;
		return 
			(this.id == castOther.id)
			&& this.name.equals(castOther.name);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.name.hashCode();
		
		return hash;
	}
}