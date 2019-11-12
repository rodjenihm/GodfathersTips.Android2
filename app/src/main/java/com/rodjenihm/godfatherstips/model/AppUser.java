package com.rodjenihm.godfatherstips.model;

import java.io.Serializable;
import java.util.Date;

public class AppUser implements Serializable {
    private int accessLevel = 1;
    private Date createdAt = new Date();
    private String email;
    private String userId;

    public AppUser() {
    }

    public AppUser(String userId, String email, Date createdAt, int accessLevel) {
        this.accessLevel = accessLevel;
        this.createdAt = createdAt;
        this.email = email;
        this.userId = userId;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
