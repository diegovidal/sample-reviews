package com.dvidal.samplereviews.features.list.domain

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.Either
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.core.datasource.local.LocalConstants
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import com.dvidal.samplereviews.features.list.data.local.config.ConfigLocalDataSource
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewDto
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewsLocalDataSource
import com.dvidal.samplereviews.features.list.data.remote.ReviewsRemoteDataSource

/**
 * @author diegovidal on 2020-02-15.
 */
class ReviewsRepositoryImpl(
    private val reviewsLocalDataSource: ReviewsLocalDataSource,
    private val reviewsRemoteDataSource: ReviewsRemoteDataSource,
    private val configLocalDataSource: ConfigLocalDataSource
) : ReviewsRepository {

    override suspend fun requestReviews(): EitherResult<Unit> {

        return try {

            val configDto = configLocalDataSource.fetchConfig().rightOrNull()
            if (configDto == null) {
                // First time
                val remoteResult = reviewsRemoteDataSource.fetchReviews()
                if (remoteResult.isRight) {

                    // Save config for first time
                    remoteResult.rightOrNull()?.mapperToConfigDto()?.let { configDtoForSave ->
                        configLocalDataSource.insertConfig(configDtoForSave)
                    }
                }
            }

            Either.right(Unit)
        } catch (t: Throwable) {

            Either.left(t)
        }
    }

    override fun fetchConfig(): EitherResult<LiveData<ConfigDto?>> {
        return configLocalDataSource.fetchConfigAsLiveData()
    }

    override fun fetchReviews(): EitherResult<LiveData<List<ReviewDto>>> {
        return reviewsLocalDataSource.fetchReviews()
    }

    override suspend fun toggleSortByRating(): EitherResult<Unit> {
        return configLocalDataSource.toggleSortByRating()
    }

    override suspend fun clearCache(): EitherResult<Unit> {

        return try {
            reviewsLocalDataSource.clearReviews()
            configLocalDataSource.clearConfig()
        } catch (t: Throwable) {
            Either.left(t)
        }
    }
}