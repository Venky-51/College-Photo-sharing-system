package com.college.photoweb.model;

public class DeleteRequest {

    private String event;
    private String image;
    private String reason;

    public DeleteRequest() {
    }

    public DeleteRequest(String event, String image, String reason) {
        this.event = event;
        this.image = image;
        this.reason = reason;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
