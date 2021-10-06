package com.example.aws_push_sample.RegisterFunction;

import java.io.Serializable;

public class RegisterRequestObject implements Serializable {
    /*
        "app_reg_id": "",
        "device_token": "d2ac3afda52c0618e0f597c08a3d2209553f119fa363b778eeee789a87f296d1",
        "ori_device_token": "",
        "app_name": "BEA APP",
        "mobile_type":"iOS",
        "app_id":"BA"
     */
    private String app_reg_id;
    private String device_token;
    private String ori_device_token;
    private String app_name;
    private String mobile_type;
    private String app_id;

    public RegisterRequestObject(String app_reg_id, String device_token, String ori_device_token, String app_name, String mobile_type, String app_id) {
        this.app_reg_id = app_reg_id;
        this.device_token = device_token;
        this.ori_device_token = ori_device_token;
        this.app_name = app_name;
        this.mobile_type = mobile_type;
        this.app_id = app_id;
    }

    public String getApp_reg_id() {
        return app_reg_id;
    }

    public void setApp_reg_id(String app_reg_id) {
        this.app_reg_id = app_reg_id;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getOri_device_token() {
        return ori_device_token;
    }

    public void setOri_device_token(String ori_device_token) {
        this.ori_device_token = ori_device_token;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getMobile_type() {
        return mobile_type;
    }

    public void setMobile_type(String mobile_type) {
        this.mobile_type = mobile_type;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
}
