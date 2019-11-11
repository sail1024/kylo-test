package com.stella.test.jcr;

import org.modeshape.jcr.api.txn.TransactionManagerLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.*;

/**
 * transaction manager.
 *
 * @author sail
 * @date 18:04 2019-11-11.
 * @since 1.0
 */
@Service
public class TransactionManager {

    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);

    @Autowired
    private TransactionManagerLookup transactionManagerLookup;

    public void begin() {

        try {
            transactionManagerLookup.getTransactionManager().begin();
        } catch (Exception e){
            logger.error("begin transaction error", e);
        }
    }

    public void commit() {
        try {
            transactionManagerLookup.getTransactionManager().commit();
        } catch (Exception e){
            logger.error("commit transaction error", e);
        }
    }

    public void rollback() {
        try {
            transactionManagerLookup.getTransactionManager().rollback();
        } catch (SystemException e) {
            logger.error("rollback error", e);
        }
    }

    public void doWork(Runnable work){
        begin();
        try {
            work.run();

            commit();
        }catch (Exception e){
            rollback();
        }
    }
}
