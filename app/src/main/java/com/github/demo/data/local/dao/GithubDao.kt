package com.github.demo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.demo.data.models.Repository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

import io.reactivex.Single

@Dao
interface GithubDao {

    @Insert
    fun insert(repos: Repository): Completable

    @Delete
    fun delete(repo: Repository): Single<Int>

    @Query("SELECT * FROM repository")
    fun getAllRepos(): Flowable<List<Repository>>
}