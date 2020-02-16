package com.dvidal.samplereviews.features.list.domain.usecases

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.UseCase
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewDto
import com.dvidal.samplereviews.features.list.domain.ReviewsRepository
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */

@Reusable
class FetchReviewsUseCase @Inject constructor(
    private val repository: ReviewsRepository
) : UseCase<LiveData<List<ReviewDto>>, UseCase.None>() {

    override suspend fun run(params: None) = repository.fetchReviews()
}