/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springtest2_rest_mysql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author makis
 */
@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	Employee createNewEmployee(Employee newEmployee) {
		return employeeRepository.save(newEmployee);
	}

	Employee findEmployeeById(Long id) {
		return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	List<Employee> findEmployeeByName(String name) {
		return employeeRepository.findByName(name);
	}
	
	Employee findRecruitmentByRecruitmentTime(LocalTime recruitmentTime) {
		return employeeRepository.findRecruitmentByRecruitmentTime(recruitmentTime);
	}
	
	List<Employee> findEmployeeByRecruitmentDate(LocalDate recruitmentDate) {
		return employeeRepository.findEmployeeByRecruitmentDate(recruitmentDate);
	}
	
	Employee findLastPaidEmployee() {
		return employeeRepository.findLastPaidEmployee();
	}
	
	List<Employee> findAllActiveEmployees() {
		return employeeRepository.findAllActiveEmployees();
	}

	Employee updateEmployee(Employee newEmployee, Long id) {

		return employeeRepository.findById(id).map(employee -> {
			employee.setName(newEmployee.getName());
			employee.setRole(newEmployee.getRole());
			employee.setStatus(newEmployee.getStatus());
			return employeeRepository.save(employee);
		}).orElseGet(() -> {
//                    newEmployee.setId(id);
			return employeeRepository.save(newEmployee);
		});
	}

	void deleteEmployeeById(Long id) {
		employeeRepository.deleteById(id);
	}
}
