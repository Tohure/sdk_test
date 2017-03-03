package com.gec.sdktestnotification.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tohure on 03/03/17.
 */

public class TokenResponse {

    @SerializedName("error")
    private int error;

    @SerializedName("message")
    private String message;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
