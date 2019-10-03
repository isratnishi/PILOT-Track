package com.opus_bd.pilot.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Req {

    @SerializedName("orgID")
    @Expose
    private Integer orgID;
    @SerializedName("reqNo")
    @Expose
    private String reqNo;
    @SerializedName("reqDate")
    @Expose
    private String reqDate;
    @SerializedName("startPort")
    @Expose
    private String startPort;
    @SerializedName("endPort")
    @Expose
    private String endPort;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("noOfToken")
    @Expose
    private Integer noOfToken;
    @SerializedName("noOfShip")
    @Expose
    private Integer noOfShip;
    @SerializedName("tokenUnitValue")
    @Expose
    private Double tokenUnitValue;
    @SerializedName("estimatedCost")
    @Expose
    private Double estimatedCost;
    @SerializedName("tripStartDate")
    @Expose
    private String tripStartDate;
    @SerializedName("requisitionStatusId")
    @Expose
    private Integer requisitionStatusId;
    @SerializedName("requisitionStatus")
    @Expose
    private Object requisitionStatus;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("entryBy")
    @Expose
    private Object entryBy;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("updateBy")
    @Expose
    private Object updateBy;
    @SerializedName("updateDate")
    @Expose
    private Object updateDate;

    public Integer getOrgID() {
        return orgID;
    }

    public void setOrgID(Integer orgID) {
        this.orgID = orgID;
    }

    public String getReqNo() {
        return reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getNoOfToken() {
        return noOfToken;
    }

    public void setNoOfToken(Integer noOfToken) {
        this.noOfToken = noOfToken;
    }

    public Integer getNoOfShip() {
        return noOfShip;
    }

    public void setNoOfShip(Integer noOfShip) {
        this.noOfShip = noOfShip;
    }

    public Double getTokenUnitValue() {
        return tokenUnitValue;
    }

    public void setTokenUnitValue(Double tokenUnitValue) {
        this.tokenUnitValue = tokenUnitValue;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public Integer getRequisitionStatusId() {
        return requisitionStatusId;
    }

    public void setRequisitionStatusId(Integer requisitionStatusId) {
        this.requisitionStatusId = requisitionStatusId;
    }

    public Object getRequisitionStatus() {
        return requisitionStatus;
    }

    public void setRequisitionStatus(Object requisitionStatus) {
        this.requisitionStatus = requisitionStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(Object entryBy) {
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