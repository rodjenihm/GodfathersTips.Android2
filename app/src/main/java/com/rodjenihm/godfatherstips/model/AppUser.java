package com.rodjenihm.godfatherstips.model;

import java.io.Serializable;
import java.util.Date;

public class AppUser implements Serializable {
    public static AppUser CURRENT_USER;

    private int accessLevel = 1;
    private Date createdAt = new Date();
    private Date lastSeen;
    private String email;
    private String userId;

    public AppUser() {
    }

    public AppUser(String userId, String email, Date createdAt, Date lastSeen, int accessLevel) {
        this.accessLevel = accessLevel;
        this.createdAt = createdAt;
        this.email = email;
        this.userId = userId;
        this.lastSeen = lastSeen;
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

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }
}
