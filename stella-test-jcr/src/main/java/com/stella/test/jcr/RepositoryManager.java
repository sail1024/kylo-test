package com.stella.test.jcr;

import org.modeshape.common.collection.Problems;
import org.modeshape.jcr.ModeShapeEngine;
import org.modeshape.jcr.RepositoryConfiguration;
import org.modeshape.jcr.api.txn.TransactionManagerLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jcr.Repository;
import java.net.URL;

/**
 * repositoryManager.
 *
 * @author sail
 * @date 12:14 2019-11-11.
 * @since 1.0
 */
@Service
public class RepositoryManager {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryManager.class);

    private ModeShapeEngine engine;

    private RepositoryConfiguration configuration;

    private Repository repository;

    public Repository getRepository() {
        return repository;
    }

    @Bean
    public TransactionManagerLookup transactionManagerLookup(){
        return configuration.getTransactionManagerLookup();
    }

    @PostConstruct
    public void init(){
        // step 1 create and start the engine ...
        engine = new ModeShapeEngine();
        engine.start();

        // step 2 load the configuration for a repository via the classloader
        try {
            URL url = getClass().getClassLoader().getResource("repository-config.json");
            configuration = RepositoryConfiguration.read(url);

            // step 3 verify the configuration for repository
            Problems problems = configuration.validate();
            if (problems.hasErrors()){
                logger.error("Problems starting the engine.");
                logger.error("{}", problems);
                return;
            }

            // step 4 deploy the repository
            repository = engine.deploy(configuration);
            String repositoryName = configuration.getName();

            logger.info("Deploy the repository {} success.", repositoryName);

            dumpRepositoryInfo();
        }catch (Exception e){
            logger.error("Get repository error", e);

        }
    }


    public void dumpRepositoryInfo(){
        logger.info("repository: {}, info:", repository.getDescriptor(Repository.REP_NAME_DESC));
        for (String key : repository.getDescriptorKeys()){
            logger.info("{} >>> {}", key, repository.getDescriptor(key));
        }
    }

    public void shutdown(){

        if (engine != null){
            try {
                engine.shutdown().get();
                logger.info("Shut down success!");
            }catch (Exception e){
                logger.error("Shut down error", e);
            }

            engine = null;

            repository = null;
        }
    }
}
