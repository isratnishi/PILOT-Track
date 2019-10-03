package com.opus_bd.pilot.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("passwordHash")
    @Expose
    private String passwordHash;
    @SerializedName("orgID")
    @Expose
    private Integer orgID;
    @SerializedName("userTypeId")
    @Expose
    private Integer userTypeId;
    @SerializedName("pilotID")
    @Expose
    private Integer pilotID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Integer getOrgID() {
        return orgID;
    }

    public void setOrgID(Integer orgID) {
        this.orgID = orgID;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Integer getPilotID() {
        return pilotID;
    }

    public void setPilotID(Integer pilotID) {
        this.pilotID = pilotID;
    }

 @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Password")
    @Expose
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }
}