package com.github.demo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.demo.data.models.Contributor
import com.github.demo.data.models.ReadMeDetails
import com.github.demo.data.models.Repository
import com.github.demo.data.remote.response.owner.OwnerDetailsResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

import io.reactivex.Single

@Dao
interface UserDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(owner: OwnerDetailsResponse): Completable

    @Query("SELECT * FROM userDetails where login=:loginKey")
    fun getUser(loginKey: String): Single<OwnerDetailsResponse>
}