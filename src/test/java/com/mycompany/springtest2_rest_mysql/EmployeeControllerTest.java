package com.mycompany.springtest2_rest_mysql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	EmployeeService employeeService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testFindAll() throws Exception {
		Employee employee = new Employee("Makis", "developer", "employed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());
		Employee employeeTwo = new Employee("Alex", "tester", "unemployed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		employees.add(employeeTwo);

		Mockito.when(employeeService.findAllEmployees()).thenReturn(employees);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", org.hamcrest.Matchers.is("Makis")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", org.hamcrest.Matchers.is("Alex")));
	}

	@Test
	public void testFindAllActiveEmployees() throws Exception {
		List<Employee> employees = new ArrayList<Employee>();
		Employee empOne = new Employee("Jerry", "developer", "employed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());
		Employee empTwo = new Employee("Alex", "tester", "unemployed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());
		Employee empThree = new Employee("Steve", "developer", "employed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());
		employees.add(empOne);
		employees.add(empTwo);
		employees.add(empThree);

		List<Employee> activeEmployees = employees.stream().filter(e -> "employed".equals(e.getStatus()))
				.collect(Collectors.toList());

		Mockito.when(employeeService.findAllActiveEmployees()).thenReturn(activeEmployees);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees/active"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", org.hamcrest.Matchers.is("Jerry")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].role", org.hamcrest.Matchers.is("developer")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].status", org.hamcrest.Matchers.is("employed")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", org.hamcrest.Matchers.is("Steve")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].role", org.hamcrest.Matchers.is("developer")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].status", org.hamcrest.Matchers.is("employed")));
	}

	@Test
	public void testNewEmployee() throws Exception {
		Employee employee = new Employee("Chris", "tester", "unemployed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());

		Mockito.when(employeeService.createNewEmployee(employee)).thenReturn(employee);

		mockMvc.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectToJsonString(employee))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", org.hamcrest.Matchers.is("Chris")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.role", org.hamcrest.Matchers.is("tester")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status", org.hamcrest.Matchers.is("unemployed")));
	}

	@Test
	public void testFindEmployeeById() throws Exception {
		Employee employee = new Employee("Chris", "testet", "unemployed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());

		Mockito.when(employeeService.findEmployeeById(1L)).thenReturn(employee);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 1L))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", org.hamcrest.Matchers.is("Chris")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.role", org.hamcrest.Matchers.is("testet")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status", org.hamcrest.Matchers.is("unemployed")));
	}

	@Test
	public void testFindEmployeeByName() throws Exception {
		Employee employee = new Employee("Chris", "testet", "unemployed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());
		Employee employeeTwo = new Employee("Makis", "developer", "employed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());
		Employee employeeThree = new Employee("Chris", "developer", "employed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		employees.add(employeeTwo);
		employees.add(employeeThree);

		List<Employee> employeesWithNameChris = employees.stream().filter(e -> "Chris".equals(e.getName()))
				.collect(Collectors.toList());

		Mockito.when(employeeService.findEmployeeByName("Chris")).thenReturn(employeesWithNameChris);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees/by?name=Chris"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", org.hamcrest.Matchers.is("Chris")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].role", org.hamcrest.Matchers.is("testet")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].status", org.hamcrest.Matchers.is("unemployed")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", org.hamcrest.Matchers.is("Chris")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].role", org.hamcrest.Matchers.is("developer")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].status", org.hamcrest.Matchers.is("employed")));
	}

	@Test
	public void testReplaceEmployee() throws Exception {
		Employee empOne = new Employee("Jerry", "developer", "employed",
				LocalDateTime.of(LocalDate.of(2021, 3, 31), LocalTime.now()), LocalDate.of(2021, 3, 31),
				LocalTime.now());

		Mockito.when(employeeService.updateEmployee(empOne, 1L)).thenReturn(empOne);

		mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.content(objectToJsonString(empOne))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", org.hamcrest.Matchers.is("Jerry")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.role", org.hamcrest.Matchers.is("developer")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status", org.hamcrest.Matchers.is("employed")));
	}

	@Test
	public void testDeleteEmployee() throws Exception {
		Mockito.doNothing().when(employeeService).deleteEmployeeById(1L);

		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", 1L))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	private static String objectToJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
