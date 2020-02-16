package com.dvidal.samplereviews.features.list.domain.usecases

import com.dvidal.samplereviews.features.list.domain.ReviewsRepository
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */

@Reusable
class FetchReviewsUseCase @Inject constructor(
    private val repository: ReviewsRepository
) {
}