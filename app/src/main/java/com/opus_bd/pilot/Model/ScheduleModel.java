package com.opus_bd.pilot.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleModel {
    @SerializedName("pilotID")
    @Expose
    private Integer pilotID;
    @SerializedName("scheduleID")
    @Expose
    private Integer scheduleID;
    @SerializedName("pilotName")
    @Expose
    private String pilotName;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("scheduleNo")
    @Expose
    private String scheduleNo;
    @SerializedName("shipName")
    @Expose
    private String shipName;

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

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

}
