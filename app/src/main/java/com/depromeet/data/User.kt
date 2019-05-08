package com.depromeet.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User constructor(
        @Expose
        @SerializedName("id")
        val id: Int,

        @Expose
        @SerializedName("name")
        val name: String
) {
    constructor(name: String) : this(-1, name)
}