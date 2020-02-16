package com.dvidal.samplereviews.features.list.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dvidal.samplereviews.core.common.Either
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import com.dvidal.samplereviews.features.list.data.local.config.ConfigLocalDataSource
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewDto
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewsLocalDataSource
import com.dvidal.samplereviews.features.list.data.remote.ReviewsRemoteDataSource
import com.dvidal.samplereviews.features.list.presentation.ReviewsPageView
import com.dvidal.samplereviews.utils.getOrAwaitValue
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

/**
 * @author diegovidal on 2020-02-15.
 */
class ReviewsRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val reviewsLocalDataSource = mockk<ReviewsLocalDataSource>()
    private val reviewsRemoteDataSource = mockk<ReviewsRemoteDataSource>()
    private val configLocalDataSource = mockk<ConfigLocalDataSource>()

    private lateinit var repository: ReviewsRepository

    @Before
    fun setup() {
        repository = ReviewsRepositoryImpl(
            reviewsLocalDataSource,
            reviewsRemoteDataSource,
            configLocalDataSource
        )
    }

    @Test
    fun `when fetch reviews should call reviewsLocalDataSource fetch reviews and return reviewsDto`() {

        val expectedReviewsDto = listOf(ReviewDto(100), ReviewDto(101))
        val liveData = MutableLiveData(expectedReviewsDto)
        every { reviewsLocalDataSource.fetchReviews() } returns Either.right(liveData)

        val reviewsDto = repository.fetchReviews().rightOrNull()
        verify(exactly = 1) { reviewsLocalDataSource.fetchReviews() }
        assertEquals(expectedReviewsDto, reviewsDto?.getOrAwaitValue())
    }

    @Test
    fun `when fetch config should call configLocalDataSource fetch config and return configDto`() {

        val expectedConfigDto = ConfigDto()
        val liveData = MutableLiveData(expectedConfigDto)
        every { configLocalDataSource.fetchConfigAsLiveData() } returns Either.right(liveData)

        val configDto = repository.fetchConfig().rightOrNull()
        verify(exactly = 1) { configLocalDataSource.fetchConfigAsLiveData() }
        assertEquals(expectedConfigDto, configDto?.getOrAwaitValue())
    }

    @Test
    fun `when toggle sort by rating should call configLocalDataSource toggle sort by rating `() =
        runBlocking {

            coEvery { configLocalDataSource.toggleSortByRating() } returns Either.right(Unit)

            repository.toggleSortByRating()
            coVerify(exactly = 1) { configLocalDataSource.toggleSortByRating() }
        }

    @Test
    fun `when clear cache should call configLocalDataSource clear config and reviewsLocalDataSource clear reviews`() =
        runBlocking {

            coEvery { configLocalDataSource.clearConfig() } returns Either.right(Unit)
            coEvery { reviewsLocalDataSource.clearReviews() } returns Either.right(Unit)

            repository.clearCache()
            coVerify(exactly = 1) { configLocalDataSource.clearConfig() }
            coVerify(exactly = 1) { reviewsLocalDataSource.clearReviews() }
        }

    @Ignore("Just for now")
    @Test
    fun `when request reviews and has not config on local database should call for remote and save config`() = runBlocking {

        val configLiveData = MutableLiveData<ConfigDto?>(null)
        coEvery { configLocalDataSource.fetchConfigAsLiveData() } returns EitherResult.right(configLiveData)

        val reviewsPageView = ReviewsPageView.empty()
        coEvery { reviewsRemoteDataSource.fetchReviews(any()) } returns EitherResult.right(reviewsPageView)

        repository.requestReviews()
        coVerify(exactly = 1) { reviewsRemoteDataSource.fetchReviews(any()) }
        coVerify(exactly = 1) { configLocalDataSource.insertConfig(any(), any(), any()) }
    }
}