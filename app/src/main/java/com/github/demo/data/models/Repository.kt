package com.github.demo.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "repository")
data class Repository(

    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = 0,

    @ColumnInfo(name = "repoName")
    val repoName: String,

    @ColumnInfo(name = "repoDesc")
    val repoDesc: String?,

    @ColumnInfo(name = "devName")
    val devName: String,

    @ColumnInfo(name = "devAvatar")
    val devAvatar: String


) : Parcelable
