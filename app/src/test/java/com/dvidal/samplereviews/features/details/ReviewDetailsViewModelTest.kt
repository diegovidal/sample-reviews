package com.dvidal.samplereviews.features.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvidal.samplereviews.core.common.BaseCoroutineDispatcher
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import com.dvidal.samplereviews.utils.getOrAwaitValue
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author diegovidal on 2020-02-18.
 */
class ReviewDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = mockk<BaseCoroutineDispatcher>()

    private lateinit var viewModel: ReviewDetailsViewModel

    @Before
    fun setup() {

        viewModel = ReviewDetailsViewModel(dispatcher)
    }

    @Test
    fun `when invoke user interaction with InitPageEvent should return ReviewDetailsPageScreen`() {

        val dummyReviewView = ReviewView.empty()
        viewModel.invokeUserInteraction(ReviewDetailsViewContract.UserInteraction.InitPageEvent(dummyReviewView))

        val expected = ReviewDetailsViewContract.ViewState.ReviewDetailsPageScreen(dummyReviewView)
        assertEquals(expected, viewModel.reviewsLiveEvents.getOrAwaitValue())
    }
}