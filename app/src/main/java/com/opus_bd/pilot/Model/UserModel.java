package com.opus_bd.pilot.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserModel {

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Password")
    @Expose
    private String password;
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
    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @SerializedName("RememberMe")
    @Expose
    private Boolean rememberMe;

    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

  /*  public UserModel(String name, String password, Boolean rememberMe) {
        this.name = name;
        this.password = password;
        this.rememberMe = rememberMe;
    }*/

    public UserModel() {
    }

    @SerializedName("remember_token")
    @Expose
    private Object rememberToken;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
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