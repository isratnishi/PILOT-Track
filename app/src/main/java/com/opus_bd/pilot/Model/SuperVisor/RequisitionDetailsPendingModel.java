package com.opus_bd.pilot.Model.SuperVisor;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequisitionDetailsPendingModel {

    @SerializedName("reqID")
    @Expose
    private Integer reqID;
    @SerializedName("req")
    @Expose
    private Req req;
    @SerializedName("shipNo")
    @Expose
    private String shipNo;
    @SerializedName("shipName")
    @Expose
    private String shipName;
    @SerializedName("scheduleUptoPort")
    @Expose
    private String scheduleUptoPort;
    @SerializedName("destinationPort")
    @Expose
    private String destinationPort;
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

    public Integer getReqID() {
        return reqID;
    }

    public void setReqID(Integer reqID) {
        this.reqID = reqID;
    }

    public Req getReq() {
        return req;
    }

    public void setReq(Req req) {
        this.req = req;
    }

    public String getShipNo() {
        return shipNo;
    }

    public void setShipNo(String shipNo) {
        this.shipNo = shipNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getScheduleUptoPort() {
        return scheduleUptoPort;
    }

    public void setScheduleUptoPort(String scheduleUptoPort) {
        this.scheduleUptoPort = scheduleUptoPort;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
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
