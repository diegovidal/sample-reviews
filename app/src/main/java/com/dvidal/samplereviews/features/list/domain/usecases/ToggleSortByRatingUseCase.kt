package com.dvidal.samplereviews.features.list.domain.usecases

import com.dvidal.samplereviews.core.common.UseCase
import com.dvidal.samplereviews.features.list.domain.ReviewsRepository
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */

@Reusable
class ToggleSortByRatingUseCase @Inject constructor(
    private val repository: ReviewsRepository
) : UseCase<Unit, UseCase.None>() {

    override suspend fun run(params: None) = repository.toggleSortByRating()
}