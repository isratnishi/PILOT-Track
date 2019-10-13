package com.opus_bd.pilot.Model.Organization;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BoatStatusForORG {

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
    @SerializedName("pilotName")
    @Expose
    private String pilotName;
    @SerializedName("shipName")
    @Expose
    private String shipName;
    @SerializedName("checkType")
    @Expose
    private String checkType;
    @SerializedName("beatName")
    @Expose
    private String beatName;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("entryTime")
    @Expose
    private String entryTime;
    @SerializedName("location")
    @Expose
    private String location;

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

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getBeatName() {
        return beatName;
    }

    public void setBeatName(String beatName) {
        this.beatName = beatName;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
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
