/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springtest2_rest_mysql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author makis
 */
@RequestMapping("/employees")
@RestController
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	ResponseEntity<List<Employee>> findAll() {
		return ResponseEntity.ok(employeeService.findAllEmployees());
	}

	@PostMapping
	ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee) {
		return ResponseEntity.ok(employeeService.createNewEmployee(newEmployee));
	}

	@GetMapping("/{id}")
	ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.findEmployeeById(id));
	}

	@GetMapping("/active")
	ResponseEntity<List<Employee>> findAllActiveEmployees() {
		List<Employee> employees = employeeService.findAllActiveEmployees();
		if (employees != null) {
			return ResponseEntity.ok(employees);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/by")
	ResponseEntity<List<Employee>> findEmployeeByName(@RequestParam String name) {
		List<Employee> employeeInDb = employeeService.findEmployeeByName(name);
		if (employeeInDb != null) {
			return ResponseEntity.ok(employeeInDb);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/bytime")
	ResponseEntity<Employee> findRecruitmentByRecruitmentTime(
			@RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime recruitmentTime) {

		Employee employeeInDb = employeeService.findRecruitmentByRecruitmentTime(recruitmentTime);
		if (employeeInDb != null) {
			return ResponseEntity.ok(employeeInDb);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/bydate")
	ResponseEntity<List<Employee>> findEmployeeByRecruitmentDate(
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate recruitmentDate) {

		List<Employee> employeeInDb = employeeService.findEmployeeByRecruitmentDate(recruitmentDate);
		if (employeeInDb != null) {
			return ResponseEntity.ok(employeeInDb);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/lastpayment")
	ResponseEntity<Employee> findLastPaidEmployee() {

		Employee employeeInDb = employeeService.findLastPaidEmployee();
		if (employeeInDb != null) {
			return ResponseEntity.ok(employeeInDb);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	ResponseEntity<Employee> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return ResponseEntity.ok(employeeService.updateEmployee(newEmployee, id));
	}

	@DeleteMapping("/{id}")
	ResponseEntity<Void> DeleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployeeById(id);
		return ResponseEntity.noContent().build();
	}

}
