package com.stella.test.jcr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.QueryManager;

/**
 * session manager.
 *
 * @author sail
 * @date 13:56 2019-11-11.
 * @since 1.0
 */
@Service
public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    @Autowired
    private RepositoryManager repositoryManager;

    private Session defaultSession;

    @PostConstruct
    public void init(){
        // create a session
        try {
            defaultSession = repositoryManager
                    .getRepository()
                    .login("sail-workspace");
        } catch (RepositoryException e) {
            logger.error("Login to default workspace error", e);

            throw new BizRuntimeException("login to default workspace error", e);
        }
    }

    public Session getDefaultSession() {
        return defaultSession;
    }

    public QueryManager getQueryManager(){
        try {
            return defaultSession.getWorkspace().getQueryManager();
        } catch (RepositoryException e) {
            logger.error("get query manager error", e);
            throw new BizRuntimeException("", e);
        }
    }

    public void logout(){
        defaultSession.logout();
    }

    public void save(){
        try {
            defaultSession.save();
        } catch (RepositoryException e) {
            logger.error("do save error", e);

            throw new BizRuntimeException("save error", e);
        }
    }
}
