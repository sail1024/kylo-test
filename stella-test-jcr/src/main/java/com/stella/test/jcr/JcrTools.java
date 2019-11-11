package com.stella.test.jcr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;

/**
 * jcr tools.
 *
 * @author sail
 * @date 16:03 2019-11-11.
 * @since 1.0
 */
public class JcrTools {
    private static final Logger logger = LoggerFactory.getLogger(JcrTools.class);

    public static void dumpNode(Node node){
        try {
            String nodeType = node.getPrimaryNodeType().getName();
            logger.info("Node Type: {}", nodeType);
            PropertyIterator iterator = node.getProperties();
            while (iterator.hasNext()){
                Property property = iterator.nextProperty();

                logger.info("{}[{}] >>> {}", property.getName(), PropertyType.nameFromValue(property.getType()), property.getValue());
            }
        }catch (Exception e){
            logger.error("", e);
        }

    }
}
