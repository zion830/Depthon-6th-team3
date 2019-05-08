package com.depromeet.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Poem(
        @Expose
        @SerializedName("id")
        var id: Int,

        @Expose
        @SerializedName("username")
        var userName: String?,

        @Expose
        @SerializedName("wordFirst")
        var wordFirst: String?,

        @Expose
        @SerializedName("wordSecond")
        var wordSecond: String?,

        @Expose
        @SerializedName("wordThird")
        var wordThird: String?,

        @Expose
        @SerializedName("likeCount")
        var likeCount: Int,

        @Expose
        @SerializedName("like")
        var isLike: Boolean
)
