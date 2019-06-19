package com.opus_bd.pilot.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckinModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pilotID")
    @Expose
    private Integer pilotID;
    @SerializedName("scheduleID")
    @Expose
    private Integer scheduleID;
    @SerializedName("checkType")
    @Expose
    private String checkType;
    @SerializedName("entryTime")
    @Expose
    private String entryTime;
    @SerializedName("location")
    @Expose
    private String location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPilotID() {
        return pilotID;
    }

    public void setPilotID(Integer pilotID) {
        this.pilotID = pilotID;
    }

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}