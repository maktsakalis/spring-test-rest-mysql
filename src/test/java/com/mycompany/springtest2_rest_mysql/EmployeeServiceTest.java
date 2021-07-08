package com.mycompany.springtest2_rest_mysql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@InjectMocks
	EmployeeService service;

	@Mock
	EmployeeRepository repository;

	@Test
	void testFindAllEmployees() {
		List<Employee> list = new ArrayList<Employee>();
		Employee empOne = new Employee("Jerry", "developer", "employed");
		Employee empTwo = new Employee("Alex",  "tester", "employed");
		Employee empThree = new Employee("Steve", "developer", "unemployed");

		list.add(empOne);
		list.add(empTwo);
		list.add(empThree);

		Mockito.when(repository.findAll()).thenReturn(list);

		// test service method findAllEmployees()
		List<Employee> empList = service.findAllEmployees();

		assertEquals(3, empList.size());
		Mockito.verify(repository, Mockito.times(1)).findAll();
	}
	
	@Test
	void testFindAllActiveEmployees() {
		List<Employee> list = new ArrayList<Employee>();
		Employee empOne = new Employee("Jerry", "developer", "employed");
		Employee empTwo = new Employee("Alex",  "tester", "employed");
		Employee empThree = new Employee("Steve", "developer", "unemployed");

		list.add(empOne);
		list.add(empTwo);

		Mockito.when(repository.findAllActiveEmployees()).thenReturn(list);

		// test service method findAllActiveEmployees
		List<Employee> empList = service.findAllActiveEmployees();

		assertEquals(2, empList.size());
		Mockito.verify(repository, Mockito.times(1)).findAllActiveEmployees();
	}

}
