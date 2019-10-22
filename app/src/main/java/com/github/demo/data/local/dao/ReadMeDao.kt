package com.github.demo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.demo.data.models.ReadMeDetails
import com.github.demo.data.models.Repository
import io.reactivex.*

@Dao
interface ReadMeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(readMeDetails: ReadMeDetails): Completable

    @Query("SELECT * FROM readMe where repoFullName=:key")
    fun getReadme(key: String): Single<ReadMeDetails>
}