package com.dvidal.samplereviews.features.list.data.local.reviews

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author diegovidal on 2020-02-14.
 */

@Dao
interface ReviewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReviews(listReviewsDto: List<ReviewDto>)

    @Query("SELECT * FROM reviewdto ORDER BY id DESC")
    fun fetchReviews(): LiveData<List<ReviewDto>>

    @Query("DELETE FROM reviewdto")
    suspend fun clearReviews()
}