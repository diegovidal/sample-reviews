package com.dvidal.samplereviews.features

import android.content.Intent
import androidx.lifecycle.MediatorLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dvidal.samplereviews.BaseEspressoTest
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.common.SingleLiveEvent
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import com.dvidal.samplereviews.features.list.presentation.ReviewsViewContract
import com.dvidal.samplereviews.features.list.presentation.ReviewsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ReviewsFragmentTest: BaseEspressoTest() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val viewModel = mockk<ReviewsViewModel>(relaxUnitFun = true)

    private val configLiveEvents = MediatorLiveData<ReviewsViewContract.ViewState.ConfigLiveEvent>()
    private val reviewsLiveEvents = MediatorLiveData<ReviewsViewContract.ViewState.ReviewsLiveEvent>()
    private val singleLiveEvents = SingleLiveEvent<ReviewsViewContract.ViewState.SingleLiveEvent>()

    @Before
    fun setup() {

        every { viewModel.configLiveEvents } returns configLiveEvents
        every { viewModel.reviewsLiveEvents } returns reviewsLiveEvents
        every { viewModel.singleLiveEvents } returns singleLiveEvents

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(ReviewsViewModel::class.java) } returns viewModel
    }

    @Test
    fun whenReviewsLiveEvent_andReviewsPageScreen_shouldShowContentPage() {

        activityRule.launchActivity(Intent())

        runBlocking(Dispatchers.Main) {
            reviewsLiveEvents.postValue(ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageScreen(
                emptyList()))
        }

        onView(withId(R.id.rv_reviews)).check(matches(isDisplayed()))
    }

    @Test
    fun whenReviewsLiveEvent_andReviewsPageLoading_shouldShowProgressBar() {

        activityRule.launchActivity(Intent())

        runBlocking(Dispatchers.Main) {
            reviewsLiveEvents.postValue(ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageLoading)
        }

        onView(withId(R.id.srl_reviews)).check(matches(isDisplayed()))
    }

    @Test
    fun whenNavigateToReviewDetails_shouldShowReviewDetailsFragment() {

        activityRule.launchActivity(Intent())

        runBlocking(Dispatchers.Main) {
            singleLiveEvents.postValue(ReviewsViewContract.ViewState.SingleLiveEvent.NavigateToReviewDetails(
                ReviewView.empty()
            ))
        }

        onView(withId(R.id.container_review_details_content)).check(matches(isDisplayed()))
    }
}
