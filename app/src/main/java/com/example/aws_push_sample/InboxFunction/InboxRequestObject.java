package com.example.aws_push_sample.InboxFunction;

public class InboxRequestObject {
    private String token;
    private String start_datetime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }
}
