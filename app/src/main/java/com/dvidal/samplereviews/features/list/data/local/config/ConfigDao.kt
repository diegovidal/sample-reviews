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
    suspend fun insertConfig(configDto: ConfigDto)

    @Query("SELECT * FROM configdto WHERE -1 LIMIT 1")
    fun fetchConfig(): LiveData<ConfigDto>

    @Query("UPDATE configdto SET offsetPage = offsetPage + 1 WHERE id = -1")
    suspend fun incrementOffsetPage()

    @Query("UPDATE configdto SET isDescendingOrderRating = NOT isDescendingOrderRating WHERE id = -1")
    suspend fun toggleSortByRating()

    @Query("DELETE FROM configdto")
    suspend fun clearConfig()
}