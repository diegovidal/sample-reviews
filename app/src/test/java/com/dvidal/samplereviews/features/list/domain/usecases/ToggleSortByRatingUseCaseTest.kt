package com.dvidal.samplereviews.features.list.domain.usecases

import com.dvidal.samplereviews.core.common.Either
import com.dvidal.samplereviews.core.common.UseCase
import com.dvidal.samplereviews.features.list.domain.ReviewsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2020-02-16.
 */
class ToggleSortByRatingUseCaseTest {

    private val repository = mockk<ReviewsRepository>()

    private lateinit var useCase: RequestReviewsUseCase

    @Before
    fun setup() {
        useCase = RequestReviewsUseCase(repository)
    }

    @Test
    fun `when run use case should call repository request reviews`() = runBlocking {

        coEvery { repository.requestReviews() } returns Either.right(Unit)

        useCase.run(UseCase.None())
        coVerify(exactly = 1) { repository.requestReviews() }
    }
}