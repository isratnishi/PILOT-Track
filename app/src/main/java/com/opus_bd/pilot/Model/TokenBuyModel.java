package com.opus_bd.pilot.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenBuyModel {

    @SerializedName("orgID")
    @Expose
    private Integer orgID;
    @SerializedName("noOfToken")
    @Expose
    private Integer noOfToken;
    @SerializedName("paymentRef")
    @Expose
    private String paymentRef;
    @SerializedName("tokenUnitValue")
    @Expose
    private Integer tokenUnitValue;
    @SerializedName("estimatedCost")
    @Expose
    private Integer estimatedCost;

    public Integer getOrgID() {
        return orgID;
    }

    public void setOrgID(Integer orgID) {
        this.orgID = orgID;
    }

    public Integer getNoOfToken() {
        return noOfToken;
    }

    public void setNoOfToken(Integer noOfToken) {
        this.noOfToken = noOfToken;
    }

    public String getPaymentRef() {
        return paymentRef;
    }

    public void setPaymentRef(String paymentRef) {
        this.paymentRef = paymentRef;
    }

    public Integer getTokenUnitValue() {
        return tokenUnitValue;
    }

    public void setTokenUnitValue(Integer tokenUnitValue) {
        this.tokenUnitValue = tokenUnitValue;
    }

    public Integer getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Integer estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public TokenBuyModel(Integer orgID, Integer noOfToken, String paymentRef, Integer tokenUnitValue, Integer estimatedCost) {
        this.orgID = orgID;
        this.noOfToken = noOfToken;
        this.paymentRef = paymentRef;
        this.tokenUnitValue = tokenUnitValue;
        this.estimatedCost = estimatedCost;
    }

    @Override
    public String toString() {
        return "TokenBuyModel{" +
                "orgID=" + orgID +
                ", noOfToken=" + noOfToken +
                ", paymentRef='" + paymentRef + '\'' +
                ", tokenUnitValue=" + tokenUnitValue +
                ", estimatedCost=" + estimatedCost +
                '}';
    }
}
