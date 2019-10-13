package com.opus_bd.pilot.Model.Organization;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenforOrgModel {

    @SerializedName("balanceToken")
    @Expose
    private Integer balanceToken;
    @SerializedName("totalPurchase")
    @Expose
    private Integer totalPurchase;
    @SerializedName("totalUse")
    @Expose
    private Integer totalUse;

    public Integer getBalanceToken() {
        return balanceToken;
    }

    public void setBalanceToken(Integer balanceToken) {
        this.balanceToken = balanceToken;
    }

    public Integer getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(Integer totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public Integer getTotalUse() {
        return totalUse;
    }

    public void setTotalUse(Integer totalUse) {
        this.totalUse = totalUse;
    }
}
