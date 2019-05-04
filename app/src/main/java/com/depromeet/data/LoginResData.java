package com.depromeet.data;

import com.google.gson.annotations.SerializedName;

public class LoginResData {
    @SerializedName("state")
    private int state;

    @SerializedName("message")
    private String message;

    @SerializedName("response")
    private UserData userData;
}
