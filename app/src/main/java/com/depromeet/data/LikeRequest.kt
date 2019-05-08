package com.depromeet.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LikeRequest(
        @SerializedName("hangshi_id")
        @Expose
        val hangshiId: Int,

        @SerializedName("user_id")
        @Expose
        val userId: Int
)