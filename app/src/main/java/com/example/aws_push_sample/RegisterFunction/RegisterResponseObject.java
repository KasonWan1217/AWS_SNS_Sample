package com.example.aws_push_sample.RegisterFunction;

public class RegisterResponseObject {
    private String app_reg_id;
    private String datetime;

    public String getApp_reg_id() {
        return app_reg_id;
    }

    public void setApp_reg_id(String app_reg_id) {
        this.app_reg_id = app_reg_id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
