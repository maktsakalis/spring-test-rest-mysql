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

/**
 *
 * @author makis
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    ResponseEntity<List<Employee>> all() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping("/employees")
    ResponseEntity<Employee> newEmployee(@RequestBody Employee newEmployee) {
        return ResponseEntity.ok(employeeService.createNewEmployee(newEmployee));
    }

    @GetMapping("employees/{id}")
    Employee findEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        //TODO: Needs correction -> Find out how we handle situations like "Not Found" in controller
//        if (employeeService.findEmployeeById(id) != null) {
//            return ResponseEntity.ok(employeeService.findEmployeeById(id));
//        }
//        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("employees/{name}")
    Employee findEmployeeByName(@PathVariable String name) {
        return employeeRepository.findByName(name).orElseThrow(() -> new EmployeeNotFoundException(name));
        //TODO: Needs correction -> Find out how we handle situations like "Not Found" in controller
//        if (employeeService.findEmployeeById(id) != null) {
//            return ResponseEntity.ok(employeeService.findEmployeeById(id));
//        }
//        return ResponseEntity.notFound().build();
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
