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

        val expectedRemoteResponse = ReviewsRemoteResponse.empty()
        coEvery { remoteApi.fetchReviews(any(), any(), any()) } returns expectedRemoteResponse

        val remoteResponse = remoteDataSource.fetchReviews().rightOrNull()
        coVerify(exactly = 1) {remoteApi.fetchReviews(any(), any(), any())}
        assertEquals(expectedRemoteResponse, remoteResponse)
    }
}