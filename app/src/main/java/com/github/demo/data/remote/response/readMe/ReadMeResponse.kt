package com.github.demo.data.remote.response.readMe


import com.google.gson.annotations.SerializedName

data class ReadMeResponse(
    @SerializedName("content")
    val content: String,
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("encoding")
    val encoding: String,
    @SerializedName("git_url")
    val gitUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("_links")
    val links: Links,
    @SerializedName("name")
    val name: String,
    @SerializedName("path")
    val path: String,
    @SerializedName("sha")
    val sha: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)