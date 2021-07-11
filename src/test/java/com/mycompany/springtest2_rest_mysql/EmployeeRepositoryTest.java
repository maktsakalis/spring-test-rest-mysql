package com.mycompany.springtest2_rest_mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	void testSaveEmployee() {
		Employee employee = new Employee("Lokesh", "Engineer", "employed");
		employeeRepository.save(employee);
		java.util.List<Employee> employees = employeeRepository.findByName("Lokesh");
		assertNotNull(employee);
		assertEquals(employees.get(0).getName(), employee.getName());
	}

	@Test
	void testFindEmployeeById() {
		Employee employee = new Employee("Lokesh", "Engineer", "employed");
		employeeRepository.save(employee);
		Employee employeeFromDB = employeeRepository.findById(employee.getId()).get();
		assertEquals(employeeFromDB.getId(), employee.getId());
	}

	@Test
	void testFindAllEmployees() {
		Employee employee = new Employee("Lokesh", "Engineer", "employed");
		employeeRepository.save(employee);
		Iterable<Employee> employees = employeeRepository.findAll();
		Assertions.assertThat(employees).extracting(Employee::getName).contains("Lokesh");
	}
	
	@Test
	void testActiveEmployees() {
		Employee employee1 = new Employee("Lokesh", "Engineer", "employed");
		employeeRepository.save(employee1);
		Employee employee2 = new Employee("Gupta", "Engineer", "unemployed");		
		employeeRepository.save(employee2);
		Iterable<Employee> employees = employeeRepository.findAllActiveEmployees();
		Assertions.assertThat(employees).extracting(Employee::getName).contains("Lokesh");
	}

	@Test
	void testDeleteEmployeeById() {
		Employee employee = new Employee("Lokesh", "Engineer", "employed");
		employeeRepository.save(employee);
		assertEquals(employeeRepository.findById(employee.getId()).isPresent(), true);
		employeeRepository.deleteById(employee.getId());
		assertEquals(employeeRepository.findAll().size(), 0);
	}

}
