package com.github.demo.data.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "contributor")
data class Contributor(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = 0,

    @ColumnInfo(name = "contributor")
    val contributor: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "repoFullName")
    val repoFullName: String


) : Parcelable
