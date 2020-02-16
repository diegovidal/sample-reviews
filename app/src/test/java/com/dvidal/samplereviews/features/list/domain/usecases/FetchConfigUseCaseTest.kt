package com.dvidal.samplereviews.features.list.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.dvidal.samplereviews.core.common.Either
import com.dvidal.samplereviews.core.common.UseCase
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import com.dvidal.samplereviews.features.list.domain.ReviewsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2020-02-16.
 */
class FetchConfigUseCaseTest {

    private val repository = mockk<ReviewsRepository>()

    private lateinit var useCase: FetchConfigUseCase

    @Before
    fun setup() {
        useCase = FetchConfigUseCase(repository)
    }

    @Test
    fun `when run use case should call repository fetch config`() = runBlocking {

        val configDtoLiveData = MutableLiveData(ConfigDto())
        every { repository.fetchConfig() } returns Either.right(configDtoLiveData)

        useCase.run(UseCase.None())
        verify(exactly = 1) { repository.fetchConfig() }
    }
}