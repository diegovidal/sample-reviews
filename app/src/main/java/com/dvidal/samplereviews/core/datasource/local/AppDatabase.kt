package com.dvidal.samplereviews.core.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDao
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewDto
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewsDao

/**
 * @author diegovidal on 2020-02-14.
 */
@Database(entities = [
    ConfigDto::class,
    ReviewDto::class
],version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun configDao(): ConfigDao
    abstract fun reviewsDao(): ReviewsDao
}