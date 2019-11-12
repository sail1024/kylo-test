package com.stella.test.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * persistence manager
 *
 * @author sail
 * @date 15:21 2019-11-12.
 * @since 1.0
 */
@Service
public class PersistenceManager {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceManager.class);

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public void doWork(Work work){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            work.exec(entityManager);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            logger.error("do work error", e);
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
    }

    public <T> T doWorkCallable(CallableWork<T> work){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T t = null;
        try {
            entityManager.getTransaction().begin();
            t = work.exec(entityManager);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            logger.error("do work error", e);
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
        return t;
    }

    public static interface Work {
        void exec(EntityManager entityManager);
    }

    public static interface CallableWork<T> {
        T exec(EntityManager entityManager);
    }
}
