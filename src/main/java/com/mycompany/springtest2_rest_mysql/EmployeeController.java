/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springtest2_rest_mysql;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sun.el.stream.Optional;

/**
 *
 * @author makis
 */
@RestController
public class EmployeeController {

	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	ResponseEntity<List<Employee>> findAll() {
		return ResponseEntity.ok(employeeService.findAllEmployees());
	}

	@PostMapping("/employees")
	ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee) {
		return ResponseEntity.ok(employeeService.createNewEmployee(newEmployee));
	}

	@GetMapping("employees/{id}")
	ResponseEntity<Employee> findEmployeeById(@PathVariable Long id) {
		Employee empl = employeeService.findEmployeeById(id);

		if (empl != null) {
			return ResponseEntity.ok(empl);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("employees/active")
	ResponseEntity<List<Employee>> findAllActiveEmployees() {
		List<Employee> employees = employeeService.findAllActiveEmployees();
		if (employees != null) {
			return ResponseEntity.ok(employees);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("employees/{name}")
	ResponseEntity<Employee> findEmployeeByName(@PathVariable String name) {
		Employee employeeInDb = employeeService.findEmployeeByName(name);
		if (employeeInDb != null) {
			return ResponseEntity.ok(employeeInDb);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/employees/{id}")
	ResponseEntity<Employee> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return ResponseEntity.ok(employeeService.updateEmployee(newEmployee, id));
	}

	@DeleteMapping("employees/{id}")
	ResponseEntity<Void> DeleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployeeById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return ResponseEntity.noContent().build();
	}

}
