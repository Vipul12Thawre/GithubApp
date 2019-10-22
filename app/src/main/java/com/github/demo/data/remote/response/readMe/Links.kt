package com.github.demo.data.remote.response.readMe


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("git")
    val git: String,
    @SerializedName("html")
    val html: String,
    @SerializedName("self")
    val self: String
)