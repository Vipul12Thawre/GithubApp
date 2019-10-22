package com.github.demo.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "readMe")
data class ReadMeDetails(

    @ColumnInfo(name = "repoFullName")
    @PrimaryKey
    var repoFullName: String="",

    @ColumnInfo(name = "downloadUrl")
    val downloadUrl: String=""

) : Parcelable