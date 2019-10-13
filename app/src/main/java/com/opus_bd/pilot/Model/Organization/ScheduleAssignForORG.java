package com.opus_bd.pilot.Model.Organization;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleAssignForORG {

    @SerializedName("orgId")
    @Expose
    private Integer orgId;
    @SerializedName("reqNo")
    @Expose
    private String reqNo;
    @SerializedName("scheduleNo")
    @Expose
    private String scheduleNo;
    @SerializedName("scheduledate")
    @Expose
    private String scheduledate;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("startPort")
    @Expose
    private String startPort;
    @SerializedName("endPort")
    @Expose
    private String endPort;
    @SerializedName("pilotName")
    @Expose
    private String pilotName;
    @SerializedName("shipName")
    @Expose
    private String shipName;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getScheduledate() {
        return scheduledate;
    }

    public void setScheduledate(String scheduledate) {
        this.scheduledate = scheduledate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
}
