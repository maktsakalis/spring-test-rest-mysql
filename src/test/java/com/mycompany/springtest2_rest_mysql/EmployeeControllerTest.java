package com.mycompany.springtest2_rest_mysql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ch.qos.logback.core.status.Status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	EmployeeService employeeService;

	@Autowired
	MockMvc mockMvc;
	
    @Test
    public void testFindAll() throws Exception {
    	Employee employee = new Employee("Makis", "developer", "employed");
        List<Employee> employees = Arrays.asList(employee);
        
        Mockito.when(employeeService.findAllEmployees()).thenReturn(employees);
 
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", org.hamcrest.Matchers.is("Makis")));
    }

    @Test
    public void testFindAllActiveEmployees() throws Exception {
		List<Employee> employees = new ArrayList<Employee>();
		Employee empOne = new Employee("Jerry", "developer", "employed");
		Employee empTwo = new Employee("Alex",  "tester", "employed");
		Employee empThree = new Employee("Steve", "developer", "unemployed");
		employees.add(empOne);
		employees.add(empTwo);


        Mockito.when(employeeService.findAllActiveEmployees()).thenReturn(employees);
 
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/active"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", org.hamcrest.Matchers.is("Jerry")));
    }


}
