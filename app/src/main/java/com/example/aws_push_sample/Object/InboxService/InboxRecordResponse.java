package com.example.aws_push_sample.Object.InboxService;

import java.io.Serializable;

public class InboxRecordResponse implements Serializable {
    private String msg_id;
    private String title;
    private String body;
    private String msg_timestamp;
    private boolean status;

    public InboxRecordResponse(String msg_id, String title, String body, String msg_timestamp) {
        this.msg_id = msg_id;
        this.title = title;
        this.body = body;
        this.msg_timestamp = msg_timestamp;
        this.status = false;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMsg_timestamp() {
        return msg_timestamp;
    }

    public void setMsg_timestamp(String msg_timestamp) {
        this.msg_timestamp = msg_timestamp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
