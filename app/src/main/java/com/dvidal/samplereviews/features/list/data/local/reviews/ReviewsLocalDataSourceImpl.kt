package com.dvidal.samplereviews.features.list.data.local.reviews

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.core.common.catching
import com.dvidal.samplereviews.core.datasource.local.AppDatabase
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-14.
 */
class ReviewsLocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : ReviewsLocalDataSource {

    override suspend fun insertAllReviews(listReviewsDto: List<ReviewDto>): EitherResult<Unit> {
        return catching { appDatabase.reviewsDao().insertAllReviews(listReviewsDto) }
    }

    override fun fetchReviews(): EitherResult<LiveData<List<ReviewDto>>> {
        return catching { appDatabase.reviewsDao().fetchReviews() }
    }

    override suspend fun clearReviews(): EitherResult<Unit> {
        return catching { appDatabase.reviewsDao().clearReviews() }
    }
}