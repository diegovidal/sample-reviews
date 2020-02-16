package com.dvidal.samplereviews.features.list.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.dvidal.samplereviews.core.common.Either
import com.dvidal.samplereviews.core.common.UseCase
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import com.dvidal.samplereviews.features.list.domain.ReviewsRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2020-02-16.
 */
class RefreshPageUseCaseTest {

    private val repository = mockk<ReviewsRepository>()
    private val requestFetchReviewsUseCase = mockk<RequestReviewsUseCase>()

    private lateinit var useCase: RefreshPageUseCase

    @Before
    fun setup() {
        useCase = RefreshPageUseCase(repository, requestFetchReviewsUseCase)
    }

    @Test
    fun `when run use case should call repository clear cache case success should call requestReviewUseCase`() = runBlocking {

        coEvery {repository.clearCache()} returns Either.right(Unit)
        coEvery {requestFetchReviewsUseCase.run(any())} returns Either.right(Unit)

        useCase.run(UseCase.None())
        coVerify(exactly = 1) { repository.clearCache() }
        coVerify(exactly = 1) { requestFetchReviewsUseCase.run(any()) }
    }

    @Test
    fun `when run use case should call repository clear cache case fail should not call requestReviewUseCase`() = runBlocking {

        coEvery {repository.clearCache()} returns Either.left(Throwable())

        useCase.run(UseCase.None())
        coVerify(exactly = 1) { repository.clearCache() }
        coVerify(exactly = 0) { requestFetchReviewsUseCase.run(any()) }
    }
}