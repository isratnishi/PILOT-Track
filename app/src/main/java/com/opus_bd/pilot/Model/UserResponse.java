
package com.opus_bd.pilot.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

;

public class UserResponse implements Serializable {

    private final static long serialVersionUID = 2170091741045683150L;
    @SerializedName("user")
    @Expose
    private UserModel user;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("error_code")
    @Expose
    private String error;

    /**
     * No args constructor for use in serialization
     */
    public UserResponse() {
    }

    /**
     * @param token
     * @param user
     */
    public UserResponse(UserModel user, String token) {
        super();
        this.user = user;
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
