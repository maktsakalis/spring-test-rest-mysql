package com.mycompany.springtest2_rest_mysql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
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
		Employee empTwo = new Employee("Alex", "tester", "employed");
		Employee empThree = new Employee("Steve", "developer", "unemployed");

		list.add(empOne);
		list.add(empTwo);
		list.add(empThree);

		Mockito.when(repository.findAll()).thenReturn(list);

		List<Employee> empList = service.findAllEmployees();

		assertEquals(3, empList.size());
		Mockito.verify(repository, Mockito.times(1)).findAll();
	}

	@Test
	void testFindEmployeeById() {

		Employee employee = new Employee("Jerry", "developer", "unemployed");
		employee.setId(1L);

		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(employee));

		Employee dbEmployee = service.findEmployeeById(1L);

		assertEquals(dbEmployee.getName(), "Jerry");
		assertEquals(dbEmployee.getRole(), "developer");
		assertEquals(dbEmployee.getStatus(), "unemployed");

		Exception exception = assertThrows(EmployeeNotFoundException.class, () -> service.findEmployeeById(200L));

		String expectedMessage = "Could not find employee: 200";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testFindEmployeeByName() {
		List<Employee> list = new ArrayList<Employee>();
		Employee empOne = new Employee("Jerry", "developer", "employed");
		Employee empTwo = new Employee("Alex", "tester", "employed");

		list.add(empOne);
		list.add(empTwo);

		List<Employee> employeesNamedAlex = list.stream().filter(e -> "Alex".equals(e.getName()))
				.collect(Collectors.toList());

		Mockito.when(repository.findByName("Alex")).thenReturn(employeesNamedAlex);

		List<Employee> empList = service.findEmployeeByName("Alex");

		assertEquals(1, empList.size());
		Assertions.assertThat(empList).extracting(Employee::getName).contains("Alex");
		Mockito.verify(repository, Mockito.times(1)).findByName("Alex");
	}

	@Test
	void testFindAllActiveEmployees() {
		List<Employee> list = new ArrayList<Employee>();
		Employee empOne = new Employee("Jerry", "developer", "employed");
		Employee empTwo = new Employee("Alex", "tester", "employed");
		Employee empThree = new Employee("Steve", "developer", "unemployed");

		list.add(empOne);
		list.add(empTwo);
		list.add(empThree);

		List<Employee> activeEmployees = list.stream().filter(e -> "employed".equals(e.getStatus()))
				.collect(Collectors.toList());

		Mockito.when(repository.findAllActiveEmployees()).thenReturn(activeEmployees);

		List<Employee> empList = service.findAllActiveEmployees();

		assertEquals(2, empList.size());
		Mockito.verify(repository, Mockito.times(1)).findAllActiveEmployees();
	}

	@Test
	void testUpdateEmployee() {

		Employee employee = new Employee("Jerry", "developer", "unemployed");
		employee.setId(1L);
		Employee newEmployee = new Employee("Makis", "tester", "employed");
		newEmployee.setId(1L);

		Mockito.when(repository.findById(1L)).thenReturn(Optional.of(employee));

		service.updateEmployee(newEmployee, 1L);
		Employee updatedEmployee = service.findEmployeeById(1L);

		assertEquals(updatedEmployee.getName(), newEmployee.getName());
		assertEquals(updatedEmployee.getRole(), newEmployee.getRole());
		assertEquals(updatedEmployee.getStatus(), newEmployee.getStatus());
	}
	
	@Test
	void testCreateNewEmployee() {
		Employee employee = new Employee("Jerry", "developer", "unemployed");
		employee.setId(2L);
		
		Mockito.when(repository.save(employee)).thenReturn(employee);
		
		Employee savedEmployee = service.createNewEmployee(employee);

		assertEquals(savedEmployee.getId(), employee.getId());
		assertEquals(savedEmployee.getName(), employee.getName());
		assertEquals(savedEmployee.getRole(), employee.getRole());
		assertEquals(savedEmployee.getStatus(), employee.getStatus());
	}
	
	
	@Test
	void testDeleteEmployee() {

		Employee employee = new Employee("Jerry", "developer", "unemployed");
		employee.setId(1L);
		
		repository.save(employee);
		service.deleteEmployeeById(1L);
		
		Assertions.assertThat(repository.findById(1L)).isEmpty();
		
//		
//		Exception exception = assertThrows(EmployeeNotFoundException.class, () -> repository.findById(1L));
//
//		String expectedMessage = "Could not find employee: 200";
//		String actualMessage = exception.getMessage();
//
//		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	

}
