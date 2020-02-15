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

    private val mockRemoteResponse = ReviewsRemoteResponse.empty()

    private lateinit var remoteDataSource: ReviewsRemoteDataSource

    @Before
    fun setup() {

        remoteDataSource = ReviewsRemoteDataSourceImpl(remoteApi, networkHandler)
        every { networkHandler.isConnected } returns true
    }

    @Test
    fun `when fetch reviews should return and call remoteApi fetch reviews`() = runBlocking {

        val limitReviews = 5

        val remoteResponse = ReviewsRemoteResponse.empty()
        coEvery { remoteApi.fetchReviews(limitReviews) } returns mockRemoteResponse

        val expectedRemoteResponse = remoteDataSource.fetchReviews(limitReviews).rightOrNull()
        coVerify(exactly = 1) {remoteApi.fetchReviews(limitReviews)}
        assertEquals(expectedRemoteResponse, remoteResponse)
    }
}