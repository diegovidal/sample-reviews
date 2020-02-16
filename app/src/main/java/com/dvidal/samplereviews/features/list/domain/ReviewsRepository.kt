package com.dvidal.samplereviews.features.list.domain

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewDto

/**
 * @author diegovidal on 2020-02-15.
 */
interface ReviewsRepository {

    suspend fun requestReviews(): EitherResult<Unit>

    fun fetchConfig(): EitherResult<LiveData<ConfigDto?>>

    fun fetchReviews(): EitherResult<LiveData<List<ReviewDto>>>

    suspend fun toggleSortByRating(): EitherResult<Unit>

    suspend fun clearCache(): EitherResult<Unit>
}