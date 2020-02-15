package com.dvidal.samplereviews.features.list.data.remote

import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.features.list.presentation.ReviewsPageView

/**
 * @author diegovidal on 2020-02-15.
 */
interface ReviewsRemoteDataSource {

    suspend fun fetchReviews(limit: Int, offset: Int = 0): EitherResult<ReviewsPageView>
}