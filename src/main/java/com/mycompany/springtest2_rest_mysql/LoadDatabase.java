/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springtest2_rest_mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author makis
 */
@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {

		return args -> {
			log.info("Preloading " + employeeRepository.save(new Employee("John", "developer", "employed")));
			log.info("Preloading " + employeeRepository.save(new Employee("John", "tester", "employed")));
			log.info("Preloading " + employeeRepository.save(new Employee("George", "developer", "unemployed")));
		};
	}

}
