/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springtest2_rest_mysql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author makis
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByName(String name);

	@Query("SELECT e FROM Employee e WHERE e.lastPayment = (SELECT MAX(lastPayment) FROM Employee) ")
	Employee findLastPaidEmployee();

	Employee findRecruitmentByRecruitmentTime(LocalTime reqruitmentTime);

	List<Employee> findEmployeeByRecruitmentDate(LocalDate recruitmentDate);

	@Query("SELECT e FROM Employee e WHERE e.status = 'employed'")
	List<Employee> findAllActiveEmployees();

}
