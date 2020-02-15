package com.dvidal.samplereviews.features.list.domain

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.features.list.presentation.ConfigView
import com.dvidal.samplereviews.features.list.presentation.ReviewView

/**
 * @author diegovidal on 2020-02-15.
 */
class ReviewsRepositoryImpl: ReviewsRepository {

    override fun fetchConfig(): EitherResult<LiveData<ConfigView>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchReviews(): EitherResult<LiveData<List<ReviewView>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun toggleSortByRating(): EitherResult<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clearCache(): EitherResult<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}