package com.github.demo.data.remote.response.search


import com.google.gson.annotations.SerializedName

data class UserID(

    @SerializedName("login")
    val login: String
)