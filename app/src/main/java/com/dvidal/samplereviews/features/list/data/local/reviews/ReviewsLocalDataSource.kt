package com.dvidal.samplereviews.features.list.data.local.reviews

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.EitherResult

/**
 * @author diegovidal on 2020-02-14.
 */
interface ReviewsLocalDataSource {

    suspend fun insertAllReviews(listReviewsDto: List<ReviewDto>): EitherResult<Unit>

    fun fetchReviews(): EitherResult<LiveData<List<ReviewDto>>>

    suspend fun clearReviews(): EitherResult<Unit>
}