package com.depromeet.data;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("name")
    private String name;

    public UserData(String name) {
        this.name = name;
    }
}
