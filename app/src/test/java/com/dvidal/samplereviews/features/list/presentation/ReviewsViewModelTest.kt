package com.dvidal.samplereviews.features.list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvidal.samplereviews.core.common.BaseCoroutineDispatcher
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.features.list.domain.usecases.*
import com.dvidal.samplereviews.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author diegovidal on 2020-02-16.
 */
class ReviewsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = mockk<BaseCoroutineDispatcher>()
    private val fetchConfigUseCase = mockk<FetchConfigUseCase>(relaxUnitFun = true)
    private val fetchReviewsUseCase = mockk<FetchReviewsUseCase>(relaxUnitFun = true)
    private val refreshPageUseCase = mockk<RefreshPageUseCase>(relaxUnitFun = true)
    private val requestReviewsUseCase = mockk<RequestReviewsUseCase>(relaxUnitFun = true)
    private val toggleSortByRatingUseCase = mockk<ToggleSortByRatingUseCase>(relaxUnitFun = true)

    private lateinit var viewModel: ReviewsViewModel

    @Before
    fun setup() {

        viewModel = ReviewsViewModel(
            dispatcher = dispatcher,
            fetchConfigUseCase = fetchConfigUseCase,
            fetchReviewsUseCase = fetchReviewsUseCase,
            refreshPageUseCase = refreshPageUseCase,
            requestReviewsUseCase = requestReviewsUseCase,
            toggleSortByRatingUseCase = toggleSortByRatingUseCase
        )

        mockDispatcher()
    }

    private fun mockDispatcher() {

        every { dispatcher.IO() } returns Dispatchers.Unconfined
        every { dispatcher.Main() } returns Dispatchers.Main
    }

    @Test
    fun `when InitPageEvent should call for fetchConfigUseCase and fetchReviewsUseCase`() {

        coEvery { fetchConfigUseCase.invoke(any()) } returns EitherResult.left(Throwable())
        coEvery { fetchReviewsUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.handleUserInteraction(ReviewsViewContract.UserInteraction.InitPageEvent)
        coVerify(exactly = 1) { fetchConfigUseCase.invoke(any()) }
        coVerify(exactly = 1) { fetchReviewsUseCase.invoke(any()) }
    }

    @Test
    fun `when InitPageEvent should return ConfigPageLoading`() {

        coEvery { fetchConfigUseCase.invoke(any()) } returns EitherResult.left(Throwable())
        coEvery { fetchReviewsUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.handleUserInteraction(ReviewsViewContract.UserInteraction.InitPageEvent)
        assertEquals(ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageLoading, viewModel.configLiveEvents.getOrAwaitValue())
    }

    @Test
    fun `when InitPageEvent should return ReviewsPageLoading`() {

        coEvery { fetchConfigUseCase.invoke(any()) } returns EitherResult.left(Throwable())
        coEvery { fetchReviewsUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.handleUserInteraction(ReviewsViewContract.UserInteraction.InitPageEvent)
        assertEquals(ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageLoading, viewModel.reviewsLiveEvents.getOrAwaitValue())
    }

    @Test
    fun `when RequestReviewsEvent should call for requestReviewsUseCase`() {

        coEvery { requestReviewsUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.handleUserInteraction(ReviewsViewContract.UserInteraction.RequestReviewsEvent)
        coVerify(exactly = 1) { requestReviewsUseCase.invoke(any()) }
    }

    @Test
    fun `when ToggleSortByRatingEvent should call for toggleSortByRatingUseCase`() {

        coEvery { toggleSortByRatingUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.handleUserInteraction(ReviewsViewContract.UserInteraction.ToggleSortByRatingEvent)
        coVerify(exactly = 1) { toggleSortByRatingUseCase.invoke(any()) }
    }

    @Test
    fun `when RefreshPageEvent should call for refreshPageUseCase`() {

        coEvery { refreshPageUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.handleUserInteraction(ReviewsViewContract.UserInteraction.RefreshPageEvent)
        coVerify(exactly = 1) { refreshPageUseCase.invoke(any()) }
    }

    @Test
    fun `when invoke user interaction should post value userInteraction`() {

        val userInteraction = ReviewsViewContract.UserInteraction.InitPageEvent
        viewModel.invokeUserInteraction(userInteraction)
        assertEquals(userInteraction, viewModel.userInteraction.getOrAwaitValue())
    }
}