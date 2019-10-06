package com.opus_bd.pilot.Model.ScheduleByIDModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("scheduleNo")
    @Expose
    private String scheduleNo;
    @SerializedName("scheduleDate")
    @Expose
    private String scheduleDate;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("startPort")
    @Expose
    private String startPort;
    @SerializedName("endPort")
    @Expose
    private String endPort;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("entryBy")
    @Expose
    private String entryBy;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("updateBy")
    @Expose
    private Object updateBy;
    @SerializedName("updateDate")
    @Expose
    private Object updateDate;

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Object updateDate) {
        this.updateDate = updateDate;
    }

}