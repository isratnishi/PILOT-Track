package com.opus_bd.salestracking.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("site_id")
    @Expose
    private Integer siteId;
    @SerializedName("salesperson_id")
    @Expose
    private Integer salespersonId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("target")
    @Expose
    private String target;

    public String getTargetmeet() {
        return targetmeet;
    }

    public void setTargetmeet(String targetmeet) {
        this.targetmeet = targetmeet;
    }

    @SerializedName("targetmeet")
    @Expose
    private String targetmeet;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSalespersonId() {
        return salespersonId;
    }

    public void setSalespersonId(Integer salespersonId) {
        this.salespersonId = salespersonId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}