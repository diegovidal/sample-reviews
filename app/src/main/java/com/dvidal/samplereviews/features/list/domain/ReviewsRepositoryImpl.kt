package com.dvidal.samplereviews.features.list.domain

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.Either
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import com.dvidal.samplereviews.features.list.data.local.config.ConfigLocalDataSource
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewDto
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewsLocalDataSource
import com.dvidal.samplereviews.features.list.data.remote.ReviewsRemoteDataSource
import com.dvidal.samplereviews.features.list.data.remote.ReviewsRemoteResponse

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

            // First time
            if (configDto == null) {
                requestReviewsForFirstTime()
            }

            // Fallback
            if (configDto != null) {
                requestReviewsForFallback(configDto)
            }

            Either.right(Unit)
        } catch (t: Throwable) {

            Either.left(t)
        }
    }

    private suspend fun requestReviewsForFirstTime() {

        val remoteResult = reviewsRemoteDataSource.fetchReviews().rightOrNull()

        if (remoteResult != null) {

            // Save config for first time
            remoteResult.mapperToConfigDto().let { configDtoForSave ->
                configLocalDataSource.insertConfig(configDtoForSave)
            }

            // Save reviews on database
            val reviewsDto = remoteResult.reviews.map { it.mapperToReviewDto() }
            reviewsLocalDataSource.insertAllReviews(reviewsDto)

            // Increment offset page
            if (remoteResult.reviews.isNotEmpty())
                configLocalDataSource.incrementOffsetPage()
        }
    }

    private suspend fun requestReviewsForFallback(configDto: ConfigDto) {

        val offsetPage = configDto.offsetPage
        val remoteResult = reviewsRemoteDataSource.fetchReviews(offsetPage).rightOrNull()
        if (remoteResult != null) {

            // Save reviews on database
            val reviewsDto = remoteResult.reviews.map { it.mapperToReviewDto() }
            reviewsLocalDataSource.insertAllReviews(reviewsDto)

            // Increment offset page
            if (remoteResult.reviews.isNotEmpty())
                configLocalDataSource.incrementOffsetPage()
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