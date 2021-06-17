package com.example.aws_push_sample.InboxFunction.InboxService;

import java.io.Serializable;

public class InboxObject implements Serializable {
    private String messageID;
    private String datetime;
    private String title;
    private String content;
    private String status;

    public InboxObject(String messageID, String datetime, String title, String content, String status) {
        this.messageID = messageID;
        this.datetime = datetime;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return (status == null || "".equals(status)) ? "U" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
