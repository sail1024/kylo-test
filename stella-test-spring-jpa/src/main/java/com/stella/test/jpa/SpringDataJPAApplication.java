package com.stella.test.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * jpa example.
 *
 * @author sail
 * @date 11:47 2019-11-12.
 * @since 1.0
 */
@SpringBootApplication
@EnableTransactionManagement
public class SpringDataJPAApplication {
    public static void main(String[] args){
        SpringApplication.run(SpringDataJPAApplication.class, args);
    }
}
