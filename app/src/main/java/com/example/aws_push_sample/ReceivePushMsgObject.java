package com.example.aws_push_sample;
import androidx.annotation.Nullable;

public class ReceivePushMsgObject {

    private String msg_id;
    private String title;
    private String badge;
    private String body;
    private String sound;
    private String pic_url;
    private String type;
    @Nullable
    private String dateTime;
    @Nullable
    private String content;

    @Nullable
    public String getContent() {
        return content;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }

    @Nullable
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(@Nullable String dateTime) {
        this.dateTime = dateTime;
    }

    @Nullable
    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(@Nullable String msg_id) {
        this.msg_id = msg_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
