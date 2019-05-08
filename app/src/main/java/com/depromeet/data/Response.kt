package com.depromeet.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response(
        @Expose
        @SerializedName("status")
        private val status: Int = 0,

        @Expose
        @SerializedName("message")
        private val message: String
)