package com.opus_bd.pilot.Model.ScheduleByIDModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleByIDModel {

    @SerializedName("scheduleID")
    @Expose
    private Integer scheduleID;
    @SerializedName("schedule")
    @Expose
    private Schedule schedule;
    @SerializedName("pilotID")
    @Expose
    private Integer pilotID;
    @SerializedName("pilot")
    @Expose
    private Pilot pilot;
    @SerializedName("shipName")
    @Expose
    private String shipName;
    @SerializedName("assignBy")
    @Expose
    private Object assignBy;
    @SerializedName("assignDate")
    @Expose
    private Object assignDate;
    @SerializedName("checkinBy")
    @Expose
    private Object checkinBy;
    @SerializedName("checkinDate")
    @Expose
    private Object checkinDate;
    @SerializedName("checkoutBy")
    @Expose
    private Object checkoutBy;
    @SerializedName("checkoutDate")
    @Expose
    private Object checkoutDate;
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

    public Integer getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Integer scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Integer getPilotID() {
        return pilotID;
    }

    public void setPilotID(Integer pilotID) {
        this.pilotID = pilotID;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public Object getAssignBy() {
        return assignBy;
    }

    public void setAssignBy(Object assignBy) {
        this.assignBy = assignBy;
    }

    public Object getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Object assignDate) {
        this.assignDate = assignDate;
    }

    public Object getCheckinBy() {
        return checkinBy;
    }

    public void setCheckinBy(Object checkinBy) {
        this.checkinBy = checkinBy;
    }

    public Object getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Object checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Object getCheckoutBy() {
        return checkoutBy;
    }

    public void setCheckoutBy(Object checkoutBy) {
        this.checkoutBy = checkoutBy;
    }

    public Object getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Object checkoutDate) {
        this.checkoutDate = checkoutDate;
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