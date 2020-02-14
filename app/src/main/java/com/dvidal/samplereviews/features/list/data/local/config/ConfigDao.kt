package com.dvidal.samplereviews.features.list.data.local.config

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author diegovidal on 2020-02-14.
 */

@Dao
interface ConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addConfig(configDto: ConfigDto)

    @Query("SELECT * FROM configdto")
    fun fetchConfig(): LiveData<ConfigDto>

    @Query("DELETE FROM configdto")
    suspend fun clearConfig()
}