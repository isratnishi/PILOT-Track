package com.opus_bd.pilot.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PilotCheckBodyM {

@SerializedName("pilotID")
@Expose
private Integer pilotID;
@SerializedName("scheduleID")
@Expose
private Integer scheduleID;
@SerializedName("checkType")
@Expose
private String checkType;
@SerializedName("shipName")
@Expose
private String shipName;
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

public String getShipName() {
return shipName;
}

public void setShipName(String shipName) {
this.shipName = shipName;
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

    public PilotCheckBodyM(Integer pilotID, Integer scheduleID, String checkType, String shipName, String beatName, String entryDate, String entryTime, String location) {
        this.pilotID = pilotID;
        this.scheduleID = scheduleID;
        this.checkType = checkType;
        this.shipName = shipName;
        this.beatName = beatName;
        this.entryDate = entryDate;
        this.entryTime = entryTime;
        this.location = location;
    }
}