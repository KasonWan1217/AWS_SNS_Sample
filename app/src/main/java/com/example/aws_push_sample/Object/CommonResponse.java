package com.example.aws_push_sample.Object;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class CommonResponse {
    private Integer code;
    private List<Object> message;

    public CommonResponse(Integer code, List<Object> message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse(Integer code, String message) {
        this.code = code;
        Object[] stringArray = {message};
        List<Object> list = Arrays.asList(stringArray);
        this.message = list;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<Object> getMessage() {
        return message;
    }

    public void setMessage(List<Object> message) {
        this.message = message;
    }

    public String getList_message() {
        return new Gson().toJson(this.getMessage());
    }
    public String getString_message() {
        return (this.getMessage().size() > 0) ? new Gson().toJson(this.getMessage().get(0)) : null;
    }
}
