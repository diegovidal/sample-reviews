package com.dvidal.samplereviews.features.list.data.local.reviews

import androidx.lifecycle.MutableLiveData
import com.dvidal.samplereviews.core.datasource.local.AppDatabase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2020-02-15.
 */
class ReviewsLocalDataSourceTest {

    private val appDatabase = mockk<AppDatabase>()

    lateinit var dataSource: ReviewsLocalDataSource

    @Before
    fun setup() {
        dataSource = ReviewsLocalDataSourceImpl(appDatabase)
    }

    @Test
    fun `when insert all reviews should call reviewsDao insert all reviews`() = runBlocking {

        val dummyReviewsDto = listOf(ReviewDto(100), ReviewDto(101))
        coEvery { appDatabase.reviewsDao().insertAllReviews(dummyReviewsDto) } returns Unit

        dataSource.insertAllReviews(dummyReviewsDto)
        coVerify(exactly = 1) { appDatabase.reviewsDao().insertAllReviews(dummyReviewsDto) }
    }

    @Test
    fun `when fetch reviews should call reviewsDao fetch reviews`() = runBlocking {

        val dummyReviewsDto = listOf(ReviewDto(100), ReviewDto(101))
        val dummyLiveData = MutableLiveData(dummyReviewsDto)
        coEvery { appDatabase.reviewsDao().fetchReviews() } returns dummyLiveData

        dataSource.fetchReviews()
        coVerify(exactly = 1) { appDatabase.reviewsDao().fetchReviews() }
    }

    @Test
    fun `when clear reviews should call reviewsDao clear reviews`() = runBlocking {

        coEvery { appDatabase.reviewsDao().clearReviews() } returns Unit

        dataSource.clearReviews()
        coVerify(exactly = 1) { appDatabase.reviewsDao().clearReviews() }
    }
}