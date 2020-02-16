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
class RequestReviewsUseCaseTest {

    private val repository = mockk<ReviewsRepository>()

    private lateinit var useCase: ToggleSortByRatingUseCase

    @Before
    fun setup() {
        useCase = ToggleSortByRatingUseCase(repository)
    }

    @Test
    fun `when run use case should call repository toggle sort by rating`() = runBlocking {

        coEvery { repository.toggleSortByRating() } returns Either.right(Unit)

        useCase.run(UseCase.None())
        coVerify(exactly = 1) { repository.toggleSortByRating() }
    }
}