package com.github.demo.data.remote.response.search


import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<UserID>,
    @SerializedName("total_count")
    val totalCount: Int
)