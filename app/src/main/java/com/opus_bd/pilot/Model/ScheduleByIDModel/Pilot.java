package com.opus_bd.pilot.Model.ScheduleByIDModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pilot {

    @SerializedName("pilotName")
    @Expose
    private String pilotName;
    @SerializedName("pilotType")
    @Expose
    private String pilotType;
    @SerializedName("portID")
    @Expose
    private Integer portID;
    @SerializedName("resourceID")
    @Expose
    private Integer resourceID;
    @SerializedName("resource")
    @Expose
    private Object resource;
    @SerializedName("port")
    @Expose
    private Object port;
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

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public String getPilotType() {
        return pilotType;
    }

    public void setPilotType(String pilotType) {
        this.pilotType = pilotType;
    }

    public Integer getPortID() {
        return portID;
    }

    public void setPortID(Integer portID) {
        this.portID = portID;
    }

    public Integer getResourceID() {
        return resourceID;
    }

    public void setResourceID(Integer resourceID) {
        this.resourceID = resourceID;
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }

    public Object getPort() {
        return port;
    }

    public void setPort(Object port) {
        this.port = port;
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