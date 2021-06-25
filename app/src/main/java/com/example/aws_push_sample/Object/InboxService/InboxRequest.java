package com.example.aws_push_sample.Object.InboxService;

public class InboxRequest {
    /*
        "app_reg_id":"{{app_reg_id}}",
        "msg_timestamp": "{{reg_datetime}}"
     */
    private String app_reg_id;
    private String msg_timestamp;

    public InboxRequest(String app_reg_id, String msg_timestamp) {
        this.app_reg_id = app_reg_id;
        this.msg_timestamp = msg_timestamp;
    }

    public String getApp_reg_id() {
        return app_reg_id;
    }

    public void setApp_reg_id(String app_reg_id) {
        this.app_reg_id = app_reg_id;
    }

    public String getMsg_timestamp() {
        return msg_timestamp;
    }

    public void setMsg_timestamp(String msg_timestamp) {
        this.msg_timestamp = msg_timestamp;
    }
}
