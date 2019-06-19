package com.opus_bd.pilot.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleByDateModel {

    @SerializedName("scheduleID")
    @Expose
    private Integer scheduleID;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("scheduleNo")
    @Expose
    private String scheduleNo;
    @SerializedName("shipName")
    @Expose
    private String shipName;
    @SerializedName("startPort")
    @Expose
    private String startPort;
    @SerializedName("endPort")
    @Expose
    private String endPort;

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
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

    public String getStartPort() {
        return startPort;
    }

    public void setStartPort(String startPort) {
        this.startPort = startPort;
    }

    public String getEndPort() {
        return endPort;
    }

    public void setEndPort(String endPort) {
        this.endPort = endPort;
    }

}
