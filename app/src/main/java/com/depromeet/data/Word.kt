package com.depromeet.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Word(
        @SerializedName("hangshi")
        @Expose
        val hangshi: String
)