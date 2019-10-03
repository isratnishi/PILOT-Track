package com.opus_bd.pilot.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrganizationModel {

    @SerializedName("orgID")
    @Expose
    private Integer orgID;
    @SerializedName("noOfTokenbalance")
    @Expose
    private Integer noOfTokenbalance;
    @SerializedName("organizationName")
    @Expose
    private String organizationName;
    @SerializedName("tokenUnitValue")
    @Expose
    private Object tokenUnitValue;
    @SerializedName("estimatedCost")
    @Expose
    private Object estimatedCost;
    @SerializedName("ships")
    @Expose
    private List<Ship> ships = null;

    public Integer getOrgID() {
        return orgID;
    }

    public void setOrgID(Integer orgID) {
        this.orgID = orgID;
    }

    public Integer getNoOfTokenbalance() {
        return noOfTokenbalance;
    }

    public void setNoOfTokenbalance(Integer noOfTokenbalance) {
        this.noOfTokenbalance = noOfTokenbalance;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Object getTokenUnitValue() {
        return tokenUnitValue;
    }

    public void setTokenUnitValue(Object tokenUnitValue) {
        this.tokenUnitValue = tokenUnitValue;
    }

    public Object getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Object estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

}
