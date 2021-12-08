package com.irayasoft.pakkruti.model;

public class SmsInfo {
    public  String to;
    public String text;
    public  String img_url;

    public SmsInfo(String to, String text, String img_url) {
        this.to = to;
        this.text = text;
        this.img_url = img_url;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
