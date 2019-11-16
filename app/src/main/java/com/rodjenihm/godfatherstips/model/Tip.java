package com.rodjenihm.godfatherstips.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.rodjenihm.godfatherstips.DateConverter;

@Entity
@TypeConverters(DateConverter.class)
public class Tip {
    @PrimaryKey
    private String tipId;
    private String rivals;
    private String time;
    private String tip;
    private String odds;
    private int status;

    public Tip() {
    }

    public Tip(String tipId, String rivals, String time, String tip, String odds, int status) {
        this.tipId = tipId;
        this.rivals = rivals;
        this.time = time;
        this.tip = tip;
        this.odds = odds;
        this.status = status;
    }

    public String getTipId() {
        return tipId;
    }

    public void setTipId(String tipId) {
        this.tipId = tipId;
    }

    public String getRivals() {
        return rivals;
    }

    public void setRivals(String rivals) {
        this.rivals = rivals;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
