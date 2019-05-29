package com.opus_bd.salestracking.Model;

public class SalesModel {
    private String ProductName;
    private int SalesTarget;
    private int Target;
    private String Location;


    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getSalesTarget() {
        return SalesTarget;
    }

    public void setSalesTarget(int salesTarget) {
        SalesTarget = salesTarget;
    }

    public int getTarget() {
        return Target;
    }

    public void setTarget(int target) {
        Target = target;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
