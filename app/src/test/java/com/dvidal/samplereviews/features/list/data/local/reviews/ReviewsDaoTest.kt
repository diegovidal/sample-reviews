package com.dvidal.samplereviews.features.list.data.local.reviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.dvidal.samplereviews.core.datasource.local.AppDatabase
import com.dvidal.samplereviews.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * @author diegovidal on 2020-02-15.
 */

@RunWith(RobolectricTestRunner::class)
class ReviewsDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.systemContext,
        AppDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun `when add reviews should return reviews`() = runBlocking {

        val dummyReviewsDto = listOf(ReviewDto(id = 100), ReviewDto(id = 101))
        database.reviewsDao().insertAllReviews(dummyReviewsDto)

        val reviewsDto = database.reviewsDao().fetchReviews()
        assertEquals(2, reviewsDto.getOrAwaitValue().size)
    }

    @Test
    fun `when add and clear reviews should return nothing`() = runBlocking {

        val dummyReviewsDto = listOf(ReviewDto(id = 100), ReviewDto(id = 101))
        database.reviewsDao().insertAllReviews(dummyReviewsDto)

        var reviewsDto = database.reviewsDao().fetchReviews()
        assertEquals(2, reviewsDto.getOrAwaitValue().size)

        database.reviewsDao().clearReviews()
        reviewsDto = database.reviewsDao().fetchReviews()
        assertEquals(0, reviewsDto.getOrAwaitValue().size)

    }
}