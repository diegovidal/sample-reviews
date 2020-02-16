package com.dvidal.samplereviews.features.list.domain.usecases

import com.dvidal.samplereviews.core.common.Either
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.core.common.UseCase
import com.dvidal.samplereviews.core.datasource.remote.RemoteFailure
import com.dvidal.samplereviews.features.list.domain.ReviewsRepository
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */

@Reusable
class RefreshPageUseCase @Inject constructor(
    private val repository: ReviewsRepository,
    private val requestReviewsUseCase: RequestReviewsUseCase
): UseCase<Unit, UseCase.None>() {

    override suspend fun run(params: None): EitherResult<Unit> {

        if (repository.clearCache().isRight)
            return requestReviewsUseCase.run(None())
        return Either.left(RemoteFailure.ErrorLoadingData())
    }
}