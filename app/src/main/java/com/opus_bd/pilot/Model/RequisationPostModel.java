package com.opus_bd.pilot.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequisationPostModel {

    @SerializedName("orgID")
    @Expose
    private int orgID;
    @SerializedName("reqDate")
    @Expose
    private String reqDate;
    @SerializedName("startPortId")
    @Expose
    private int startPortId;
    @SerializedName("endPortId")
    @Expose
    private int endPortId;
    @SerializedName("departureDate")
    @Expose
    private String departureDate;
    @SerializedName("departureTime")
    @Expose
    private String departureTime;
    @SerializedName("shipId")
    @Expose
    private int shipId;


    public RequisationPostModel(int orgID, String reqDate, int startPortId, int endPortId, String departureDate, String departureTime, int shipId) {
        this.orgID = orgID;
        this.reqDate = reqDate;
        this.startPortId = startPortId;
        this.endPortId = endPortId;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.shipId = shipId;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public int getStartPortId() {
        return startPortId;
    }

    public void setStartPortId(int startPortId) {
        this.startPortId = startPortId;
    }

    public int getEndPortId() {
        return endPortId;
    }

    public void setEndPortId(int endPortId) {
        this.endPortId = endPortId;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }

    @Override
    public String toString() {
        return "RequisationPostModel{" +
                "orgID=" + orgID +
                ", reqDate='" + reqDate + '\'' +
                ", startPortId=" + startPortId +
                ", endPortId=" + endPortId +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", shipId=" + shipId +
                '}';
    }
}