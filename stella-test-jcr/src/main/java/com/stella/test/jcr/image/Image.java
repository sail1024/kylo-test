package com.stella.test.jcr.image;

import java.io.Serializable;

/**
 * image entity.
 *
 * @author sail
 * @date 16:29 2019-11-11.
 * @since 1.0
 */
public class Image implements Serializable {
    private String id;

    private String name;

    private Long width;

    private Long height;

    private String uri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
