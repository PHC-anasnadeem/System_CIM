package com.phc.cim.Activities.Notification;

import java.util.Date;

/**
 * Model class for notifications fetched from C# API
 */
public class NotificationModel {
    private int id;
    private String title;
    private String message;
    private String type;
    private Date createdDate;
    private boolean isRead;
    private String targetUrl;
    private String imageUrl;
    private int userId;

    public NotificationModel() {
    }

    public NotificationModel(int id, String title, String message, String type, Date createdDate, boolean isRead, String targetUrl, String imageUrl, int userId) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.createdDate = createdDate;
        this.isRead = isRead;
        this.targetUrl = targetUrl;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
} 