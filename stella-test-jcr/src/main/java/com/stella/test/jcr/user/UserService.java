package com.stella.test.jcr.user;

import com.stella.test.jcr.BizRuntimeException;
import com.stella.test.jcr.JcrTools;
import com.stella.test.jcr.SessionManager;
import com.stella.test.jcr.TransactionManager;
import com.stella.test.jcr.image.ImageService;
import com.stella.test.jcr.log.Log;
import com.stella.test.jcr.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.Session;

/**
 * user service
 *
 * @author sail
 * @date 14:21 2019-11-11.
 * @since 1.0
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private TransactionManager transactionManager;

    private String usersPath = "users";

    private String userNodeType = "sail:user";

    @Autowired
    private ImageService imageService;

    @Autowired
    private LogService logService;

    @PostConstruct
    public void init(){
        try {
            Session session = sessionManager.getDefaultSession();
            if (!session.getRootNode().hasNode(usersPath)){
                session.getRootNode().addNode(usersPath, "sail:users");
            }
        }catch (Exception e){
            logger.error("", e);
        }
    }

    public User addUser(User user){
        transactionManager.doWork(()->{
            try {
                UserNode userNode = findUserNodeByName(user.getName());

                Node node = userNode.node;

                // normal properties
                node.setProperty("sail:name", user.getName());
                node.setProperty("sail:nickName", user.getNickName());
                node.setProperty("sail:age", user.getAge());
                node.setProperty("sail:sex", user.getSex());
                node.setProperty("sail:password", user.getPassword());

                // avatar properties
                String avatar = user.getAvatar();

                Node imageNode = imageService.getImageById(avatar);

                if (imageNode != null){
                    node.setProperty("sail:avatar", imageNode.getIdentifier());
                }

                // add log
                Log log = new Log();
                log.setTitle("user");
                log.setAction(userNode.created ? "create" : "query");
                log.setLevel("info");
                log.setContent("create or query user node");

                logService.addLog(log);

                sessionManager.save();


                JcrTools.dumpNode(node);

//                throw new BizRuntimeException("test transaction");
            }catch (Exception e){
                logger.error("set user property error", e);

                throw new BizRuntimeException("add or update user exception", e);
            }
        });
        return user;
    }

    private UserNode findUserNodeByName(String name){
        try {
            UserNode userNode = new UserNode();

            Session session = sessionManager.getDefaultSession();

            final Node usersNode = session
                    .getRootNode()
                    .getNode(usersPath);

            final String userPath = usersPath + "/" + name;
            if (session.getRootNode().hasNode(userPath)){
                userNode.created = false;
                userNode.node = session.getRootNode().getNode(userPath);
            }else {
                userNode.created = true;
                userNode.node = usersNode.addNode(name, userNodeType);
            }

            return userNode;
        }catch (Exception e){
            logger.error("", e);

            throw new BizRuntimeException("", e);
        }
    }

    private static class UserNode {
        Node node;

        boolean created;
    }
}
