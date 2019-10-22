package com.github.demo.data.remote.response.owner


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "userDetails")
data class OwnerDetailsResponse(


    @ColumnInfo(name = "avatarUrl")
    @SerializedName("avatar_url")
    val avatarUrl: String ?,

    @ColumnInfo(name = "bio")
    @SerializedName("bio")
    val bio: String ?,

    @ColumnInfo(name = "blog")
    @SerializedName("blog")
    val blog: String ?,

    @ColumnInfo(name = "company")
    @SerializedName("company")
    val company: String ?,

    @ColumnInfo(name = "createdAt")
    @SerializedName("created_at")
    val createdAt: String = "",

    @ColumnInfo(name = "email")
    @SerializedName("email")
    val email: String?,

    @ColumnInfo(name = "eventsUrl")
    @SerializedName("events_url")
    val eventsUrl: String ?,

    @ColumnInfo(name = "followers")
    @SerializedName("followers")
    val followers: Int = 0,

    @ColumnInfo(name = "followersUrl")
    @SerializedName("followers_url")
    val followersUrl: String = "",

    @ColumnInfo(name = "following")
    @SerializedName("following")
    val following: Int = 0,

    @ColumnInfo(name = "followingUrl")
    @SerializedName("following_url")
    val followingUrl: String ?,

    @ColumnInfo(name = "gistsUrl")
    @SerializedName("gists_url")
    val gistsUrl: String ?,

    @ColumnInfo(name = "gravatarId")
    @SerializedName("gravatar_id")
    val gravatarId: String ?,

    @ColumnInfo(name = "hireable")
    @SerializedName("hireable")
    val hireable: String ?,

    @ColumnInfo(name = "htmlUrl")
    @SerializedName("html_url")
    val htmlUrl: String ?,

    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int = 0,

    @ColumnInfo(name = "location")
    @SerializedName("location")
    val location: String ?,

    @PrimaryKey
    @ColumnInfo(name = "login")
    @SerializedName("login")
    val login: String = "",

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String = "",

//    @ColumnInfo(name = "nodeId")
//    @SerializedName("node_id")
//    val nodeId: String = "",
//
//    @ColumnInfo(name = "organizationsUrl")
//    @SerializedName("organizations_url")
//    val organizationsUrl: String = "",
//
//    @ColumnInfo(name = "publicGists")
//    @SerializedName("public_gists")
//    val publicGists: Int = 0,

    @ColumnInfo(name = "publicRepos")
    @SerializedName("public_repos")
    val publicRepos: Int = 0
//
//    @ColumnInfo(name = "receivedEventsUrl")
//    @SerializedName("received_events_url")
//    val receivedEventsUrl: String = "",
//
//    @ColumnInfo(name = "reposUrl")
//    @SerializedName("repos_url")
//    val reposUrl: String = "",
//
//    @ColumnInfo(name = "siteAdmin")
//    @SerializedName("site_admin")
//    val siteAdmin: Boolean = false,
//
//    @ColumnInfo(name = "starredUrl")
//    @SerializedName("starred_url")
//    val starredUrl: String = "",
//
//    @ColumnInfo(name = "subscriptionsUrl")
//    @SerializedName("subscriptions_url")
//    val subscriptionsUrl: String = "",
//
//    @ColumnInfo(name = "type")
//    @SerializedName("type")
//    val type: String = "",
//
//    @ColumnInfo(name = "updatedAt")
//    @SerializedName("updated_at")
//    val updatedAt: String = "",
//
//    @ColumnInfo(name = "url")
//    @SerializedName("url")
//    val url: String = ""
) : Parcelable