package com.depromeet.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
        @Expose
        @SerializedName("id")
        var id: Int,

        @Expose
        @SerializedName("name")
        var name: String?
)