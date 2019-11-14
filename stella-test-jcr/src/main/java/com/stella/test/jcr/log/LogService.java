package com.stella.test.jcr.log;

import com.stella.test.jcr.BizRuntimeException;
import com.stella.test.jcr.JcrTools;
import com.stella.test.jcr.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * log service
 *
 * @author sail
 * @date 14:58 2019-11-11.
 * @since 1.0
 */
@Service
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private SessionManager sessionManager;

    private String logsPath = "logs";

    private String logType = "sail:log";

    @PostConstruct
    public void init(){
        try {
            Session session = sessionManager.getDefaultSession();
            if (!session.getRootNode().hasNode(logsPath)){
                session.getRootNode().addNode(logsPath, "sail:logs");
            }
        }catch (Exception e){
            logger.error("", e);
        }
    }

    public Node addLog(Log log){

        Long id = System.currentTimeMillis();

        String name = id.toString();

        Session session = sessionManager.getDefaultSession();

        try {
            Node logsNode = session.getRootNode().getNode(logsPath);

            Node logNode = logsNode.addNode(name, logType);
            logNode.setProperty("sail:id", id);
            logNode.setProperty("sail:action", log.getAction());
            logNode.setProperty("sail:level", log.getLevel());
            logNode.setProperty("sail:content", log.getContent());
            logNode.setProperty("jcr:title", log.getTitle());

            sessionManager.save();
            log.setId(id);

            return logNode;
        }catch (Exception e){
            logger.error("add log error", e);
        }

        return null;
    }

    public Log getLog(Long id){
        Session session = sessionManager.getDefaultSession();

        try {
            Node logsNode = session.getRootNode().getNode(logsPath);

            Node logNode = logsNode.getNode(id.toString());

            JcrTools.dumpNode(logNode);

            return convertToLog(logNode);
        }catch (Exception e){
            logger.error("get node error", e);
        }

        return null;
    }

    private Log convertToLog(Node logNode){
        try {
            Log log = new Log();
            log.setId(logNode.getProperty("sail:id").getLong());
            log.setAction(logNode.getProperty("sail:action").getString());
            log.setLevel(logNode.getProperty("sail:level").getString());
            log.setContent(logNode.getProperty("sail:content").getString());

            log.setTitle(logNode.getProperty("jcr:title").getString());

            return log;
        }catch (Exception e){
            logger.error("to log error", e);
            throw new BizRuntimeException("convert to log error", e);
        }

    }

    public List<Log> queryLog(Log log){
        Session session = sessionManager.getDefaultSession();
        List<Log> logs = new ArrayList<>();
        try {
            String statement = "SELECT * FROM [" + logType + "] AS log ";

            StringBuilder where = new StringBuilder();

            Map<String, String> params = new HashMap<>();

            if (!StringUtils.isEmpty(log.getAction())){
                params.put("action", log.getAction());
                where.append("AND [sail:action] = $action ");
            }

            if (!StringUtils.isEmpty(log.getLevel())){
                params.put("level", log.getLevel());
                where.append("AND [sail:level] = $level ");
            }

            if (log.getId() != null){
                params.put("id", log.getId().toString());
                where.append("AND [sail:id] = $id ");
            }

            if (!StringUtils.isEmpty(log.getContent())){
                params.put("content", "%" + log.getContent() + "%");
                where.append("AND LOWER([sail:content]) LIKE $content");
            }

            if (!StringUtils.isEmpty(log.getTitle())){
                params.put("title", log.getTitle() + "%");
                where.append("AND LOWER([jcr:title]) LIKE $title");
            }

            if (where.length() > 0){
                where.delete(0, 3);
                statement = statement + "WHERE " + where.toString();
            }
            Query query = sessionManager
                    .getQueryManager()
                    .createQuery(statement.toString(), "JCR-SQL2");
            for (Map.Entry<String, String> entry : params.entrySet()){
                String name = entry.getKey();
                String value = entry.getValue();
                query.bindValue(name, session.getValueFactory().createValue(value));
            }

            logger.info("[query] {}", query.getStatement());

            QueryResult result = query.execute();

            NodeIterator iterator = result.getNodes();
            while (iterator.hasNext()){
                Node node = iterator.nextNode();
                logs.add(convertToLog(node));
            }
        }catch (Exception e){
            logger.error("query log error", e);
        }
        return logs;
    }
}
