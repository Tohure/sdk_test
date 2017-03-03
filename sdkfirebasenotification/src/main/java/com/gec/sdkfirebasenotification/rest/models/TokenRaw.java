package com.gec.sdkfirebasenotification.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tohure on 03/03/17.
 */

public class TokenRaw {

    @SerializedName("name")
    private String name;

    @SerializedName("os")
    private String os;

    @SerializedName("token")
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
