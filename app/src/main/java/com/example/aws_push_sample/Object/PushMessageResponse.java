package com.example.aws_push_sample.Object;
import androidx.annotation.Nullable;

public class PushMessageResponse {

    private String msg_id;
    private int notification_id;
    private String action_category;
    private String title;
    @Nullable
    private String sub_title;
    private String body;
    private String sound;
    private int badge;
    private String pic_url;

    @Nullable
    private String dateTime;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public int getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(int notification_id) {
        this.notification_id = notification_id;
    }

    public String getAction_category() {
        return action_category;
    }

    public void setAction_category(String action_category) {
        this.action_category = action_category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(@Nullable String sub_title) {
        this.sub_title = sub_title;
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

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    @Nullable
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(@Nullable String dateTime) {
        this.dateTime = dateTime;
    }
}
