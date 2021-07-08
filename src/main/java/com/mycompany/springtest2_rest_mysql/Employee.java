/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springtest2_rest_mysql;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author makis
 */
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String role;
	private String status;

	public Employee() {
	}

	public Employee(String name, String role, String status) {
		super();
		this.name = name;
		this.role = role;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.role, this.status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Employee)) {
			return false;
		}
		final Employee other = (Employee) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.role, other.role)) {
			return false;
		}
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (!Objects.equals(this.status, other.status)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", role=" + role + ", status=" + status + "]";
	}

}
