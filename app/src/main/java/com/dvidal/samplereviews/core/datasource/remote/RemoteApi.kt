package com.dvidal.samplereviews.core.datasource.remote

import com.dvidal.samplereviews.features.list.data.remote.ReviewsRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author diegovidal on 2020-02-15.
 */
interface RemoteApi {

    @GET("activities/23776/reviews")
    suspend fun fetchReviews(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int = 0,
        @Query("sort") sort: String = "date:desc"
    ): ReviewsRemoteResponse
}