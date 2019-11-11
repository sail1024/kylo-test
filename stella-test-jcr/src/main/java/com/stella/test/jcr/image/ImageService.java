package com.stella.test.jcr.image;

import com.stella.test.jcr.BizRuntimeException;
import com.stella.test.jcr.JcrTools;
import com.stella.test.jcr.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import java.io.InputStream;

/**
 * image service
 *
 * @author sail
 * @date 16:32 2019-11-11.
 * @since 1.0
 */
@Service
public class ImageService {
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private SessionManager sessionManager;

    private String imagesPath = "images";

    private String imageType = "sail:image";

    @PostConstruct
    public void init(){
        try {
            Session session = sessionManager.getDefaultSession();
            if (!session.getRootNode().hasNode(imagesPath)){
                session.getRootNode().addNode(imagesPath, "sail:images");
            }
        }catch (Exception e){
            logger.error("", e);
        }
    }

    public Image addImage(String name, InputStream inputStream){
        Node imageNode = addImageInternal(name, inputStream);

        try {
            Image image = new Image();
            image.setName(imageNode.getName());
            image.setId(imageNode.getProperty("sail:id").getString());
            image.setWidth(imageNode.getProperty("sail:width").getLong());
            image.setHeight(imageNode.getProperty("sail:height").getLong());
            return image;
        }catch (Exception e){
            logger.error("", e);
        }


        return null;
    }

    public Node getImageById(String id){
        try {
            Session session = sessionManager.getDefaultSession();

            QueryManager queryManager = session.getWorkspace().getQueryManager();

            String statement = "SELECT * FROM [" + imageType + "] AS image WHERE [sail:id] = $id";
            Query query = queryManager.createQuery(statement, "JCR-SQL2");
            query.bindValue("id", session.getValueFactory().createValue(id));

            QueryResult result = query.execute();

            if (result.getNodes().hasNext()){
                return result.getNodes().nextNode();
            }

            return null;
        }catch (Exception e){
            logger.error("get image error", e);
            throw new BizRuntimeException("get image error");
        }
    }

    public Node addImageInternal(String name, InputStream inputStream){
        if (StringUtils.isEmpty(name)){
            name = String.valueOf(System.currentTimeMillis());
        }

        Session session = sessionManager.getDefaultSession();
        try {
            Node imagesNode = session.getRootNode().getNode(imagesPath);

            if (imagesNode.hasNode(name)){
                throw new BizRuntimeException("duplicate file name");
            }

            // Create an 'nt:file' node at the supplied path, creating any missing intermediate nodes of type 'nt:folder' ...
            Node imageNode = imagesNode.addNode(name, imageType);
            imageNode.setProperty("sail:id", String.valueOf(System.currentTimeMillis()));
            imageNode.setProperty("sail:width", 12);
            imageNode.setProperty("sail:height", 12);

            // Upload the file to that node ...
            Node contentNode = imageNode.addNode("jcr:content", "nt:resource");
            Binary binary = session.getValueFactory().createBinary(inputStream);
            contentNode.setProperty("jcr:data", binary);

            session.save();

            JcrTools.dumpNode(contentNode);

            JcrTools.dumpNode(imageNode);

            return imageNode;
        }catch (Exception e){
            logger.error("add image error", e);
            throw new BizRuntimeException("add image error", e);
        }
    }
}
