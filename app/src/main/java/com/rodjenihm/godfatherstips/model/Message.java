package com.rodjenihm.godfatherstips.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.rodjenihm.godfatherstips.DateConverter;

import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Message {
    @PrimaryKey
    @NonNull
    private String messageId;
    private String senderEmail;
    private String text;
    private Date time;

    @Ignore
    public Message() {
    }

    public Message(@NonNull String messageId, String senderEmail, String text, Date time) {
        this.messageId = messageId;
        this.senderEmail = senderEmail;
        this.text = text;
        this.time = time;
    }

    @NonNull public String getMessageId() {
        return messageId;
    }

    public void setMessageId(@NonNull String messageId) {
        this.messageId = messageId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
