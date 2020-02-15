package com.dvidal.samplereviews.features.list.domain

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.features.list.presentation.ConfigView
import com.dvidal.samplereviews.features.list.presentation.ReviewView

/**
 * @author diegovidal on 2020-02-15.
 */
interface ReviewsRepository {

    fun fetchConfig(): EitherResult<LiveData<ConfigView>>

    fun fetchReviews(): EitherResult<LiveData<List<ReviewView>>>

    suspend fun toggleSortByRating(): EitherResult<Unit>

    suspend fun clearCache(): EitherResult<Unit>
}