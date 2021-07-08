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

import antlr.collections.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTests {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	void testSaveEmployee() {
		Employee employee = new Employee("Lokesh", "Engineer", "employed");
		employeeRepository.save(employee);
		Employee employee2 = employeeRepository.findByName("Lokesh").get();
		assertNotNull(employee);
		assertEquals(employee2.getName(), employee.getName());
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
	void testDeleteEmployeeById() {
		Employee employee = new Employee("Lokesh", "Engineer", "employed");
		employeeRepository.save(employee);
		java.util.List<Employee> employees = employeeRepository.findAll();
		assertEquals(employees.size(), 1);
		employeeRepository.deleteById(employee.getId());
		assertEquals(employeeRepository.findAll().size(), 0);
	}

}
