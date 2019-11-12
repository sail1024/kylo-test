package com.stella.test.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * persistence config
 *
 * @author sail
 * @date 14:55 2019-11-12.
 * @since 1.0
 */
@Configuration
public class PersistenceConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        return Persistence.createEntityManagerFactory("SailPU");
    }
}
