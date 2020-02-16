package com.dvidal.samplereviews.features.list.data.remote

import com.dvidal.samplereviews.core.common.EitherResult

/**
 * @author diegovidal on 2020-02-15.
 */
interface ReviewsRemoteDataSource {

    suspend fun fetchReviews(offset: Int = 0): EitherResult<ReviewsRemoteResponse>
}