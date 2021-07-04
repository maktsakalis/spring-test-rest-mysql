/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.springtest2_rest_mysql;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author makis
 */
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private EmployeeController employeeController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

}
