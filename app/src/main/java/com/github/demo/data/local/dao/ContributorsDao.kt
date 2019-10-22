package com.github.demo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.demo.data.models.Contributor
import com.github.demo.data.models.ReadMeDetails
import com.github.demo.data.models.Repository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

import io.reactivex.Single

@Dao
interface ContributorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contributor: List<Contributor>): Completable

    @Query("SELECT * FROM contributor where repoFullName=:key")
    fun getContributors(key: String): Single<List<Contributor>>
}