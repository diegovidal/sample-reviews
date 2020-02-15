package com.dvidal.samplereviews.features.list.data.remote

import com.dvidal.samplereviews.core.datasource.remote.NetworkHandler
import com.dvidal.samplereviews.core.datasource.remote.RemoteApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * @author diegovidal on 2020-02-15.
 */
class ReviewsRemoteDataSourceTest {

    private val remoteApi = mockk<RemoteApi>()
    private val networkHandler = mockk<NetworkHandler>()

    private lateinit var remoteDataSource: ReviewsRemoteDataSource

    @Before
    fun setup() {

        remoteDataSource = ReviewsRemoteDataSourceImpl(remoteApi, networkHandler)
        every { networkHandler.isConnected } returns true
    }

    @Test
    fun `when fetch reviews should call remoteApi fetch reviews and return a ReviewPageView`() = runBlocking {

        val limitReviews = 5

        val remoteResponse = ReviewsRemoteResponse.empty()
        coEvery { remoteApi.fetchReviews(limitReviews) } returns remoteResponse

        val reviewsPageView = remoteDataSource.fetchReviews(limitReviews).rightOrNull()
        coVerify(exactly = 1) {remoteApi.fetchReviews(limitReviews)}
        assertEquals(remoteResponse.mapperToReviewsPageView(), reviewsPageView)
    }
}