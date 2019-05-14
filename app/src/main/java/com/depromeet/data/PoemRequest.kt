package com.depromeet.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PoemRequest(
        @Expose
        @SerializedName("username")
        var userName: String,

        @Expose
        @SerializedName("word_first")
        var wordFirst: String,

        @Expose
        @SerializedName("word_second")
        var wordSecond: String,

        @Expose
        @SerializedName("word_third")
        var wordThird: String
)
