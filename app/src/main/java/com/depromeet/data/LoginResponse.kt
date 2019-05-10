package com.depromeet.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @Expose
        @SerializedName("status")
        val status: Int,

        @Expose
        @SerializedName("message")
        val message: String,

        @Expose
        @SerializedName("response")
        val user: User
)