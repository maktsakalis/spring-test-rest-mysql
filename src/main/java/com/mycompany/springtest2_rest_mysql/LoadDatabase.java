/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springtest2_rest_mysql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

		System.out.println();

		return args -> {
			log.info("Preloading " + employeeRepository.save(new Employee("Gerasimos", "developer", "employed",
					LocalDateTime.of(LocalDate.of(2021, 9, 25), LocalTime.of(13, 15, 48)), LocalDate.of(2021, 3, 27),
					LocalTime.of(10, 43, 12))));
			log.info("Preloading " + employeeRepository.save(new Employee("Peter", "tester", "employed",
					LocalDateTime.of(LocalDate.of(2021, 9, 25), LocalTime.of(12, 38, 50)), LocalDate.of(2021, 2, 20),
					LocalTime.of(12, 30, 54))));
			log.info("Preloading " + employeeRepository.save(new Employee("George", "developer", "unemployed",
					LocalDateTime.of(LocalDate.of(2021, 9, 25), LocalTime.of(14, 59, 50)), LocalDate.of(2014, 3, 25),
					LocalTime.of(14, 30, 8))));
			log.info("Preloading " + employeeRepository.save(new Employee("Michael", "tester", "unemployed",
					LocalDateTime.of(LocalDate.of(2021, 9, 25), LocalTime.of(14, 30, 13)), LocalDate.of(2019, 1, 20),
					LocalTime.of(11, 14, 15))));
		};
	}

}
