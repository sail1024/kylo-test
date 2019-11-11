package com.stella.test.jcr.log;

import java.io.Serializable;

/**
 * log entity
 *
 * @author sail
 * @date 14:57 2019-11-11.
 * @since 1.0
 */
public class Log implements Serializable {
    private Long id;

    private String title;

    private String action;

    private String level;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
