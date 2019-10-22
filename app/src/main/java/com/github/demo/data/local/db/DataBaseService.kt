package com.github.demo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.demo.data.local.dao.ContributorsDao
import com.github.demo.data.local.dao.GithubDao
import com.github.demo.data.local.dao.ReadMeDao
import com.github.demo.data.local.dao.UserDetailsDao
import com.github.demo.data.models.Contributor
import com.github.demo.data.models.ReadMeDetails
import com.github.demo.data.models.Repository
import com.github.demo.data.remote.response.owner.OwnerDetailsResponse


@Database(
    entities = [Repository::class, ReadMeDetails::class, Contributor::class, OwnerDetailsResponse::class],
    version = 1,
    exportSchema = false
)
abstract class DataBaseService : RoomDatabase() {

    abstract fun repoDao(): GithubDao
    abstract fun readMeDao(): ReadMeDao
    abstract fun contributorsDao(): ContributorsDao
    abstract fun userDetails(): UserDetailsDao
}