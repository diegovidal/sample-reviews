package com.dvidal.samplereviews.features

import android.content.Intent
import androidx.lifecycle.MediatorLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dvidal.samplereviews.BaseEspressoTest
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.utils.RecyclerViewMatcher
import com.dvidal.samplereviews.core.common.SingleLiveEvent
import com.dvidal.samplereviews.features.details.ReviewDetailsViewContract
import com.dvidal.samplereviews.features.details.ReviewDetailsViewModel
import com.dvidal.samplereviews.features.list.presentation.ConfigView
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import com.dvidal.samplereviews.features.list.presentation.ReviewsViewContract
import com.dvidal.samplereviews.features.list.presentation.ReviewsViewModel
import io.mockk.*
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
class ReviewsFragmentTest : BaseEspressoTest() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val reviewsViewModel = mockk<ReviewsViewModel>(relaxUnitFun = true)
    private val reviewDetailsViewModel = mockk<ReviewDetailsViewModel>(relaxUnitFun = true)

    private val configLiveEvents = MediatorLiveData<ReviewsViewContract.ViewState.ConfigLiveEvent>()
    private val reviewsLiveEvents =
        MediatorLiveData<ReviewsViewContract.ViewState.ReviewsLiveEvent>()
    private val singleLiveEvents = SingleLiveEvent<ReviewsViewContract.ViewState.SingleLiveEvent>()

    @Before
    fun setup() {

        every { reviewsViewModel.configLiveEvents } returns configLiveEvents
        every { reviewsViewModel.reviewsLiveEvents } returns reviewsLiveEvents
        every { reviewsViewModel.singleLiveEvents } returns singleLiveEvents

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(ReviewsViewModel::class.java) } returns reviewsViewModel
    }

    @Test
    fun whenShowReviewsFragment_shouldCallInitPageEvent() {

        activityRule.launchActivity(Intent())

        verify(exactly = 1) { reviewsViewModel.invokeUserInteraction(ReviewsViewContract.UserInteraction.InitPageEvent) }
    }

    @Test
    fun whenReviewsLiveEvent_andReviewsPageScreen_shouldShowContentPage() {

        activityRule.launchActivity(Intent())

        runBlocking(Dispatchers.Main) {
            reviewsLiveEvents.postValue(
                ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageScreen(
                    emptyList()
                )
            )
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
    fun whenConfigLiveEvent_andConfigPageScreen_shouldShowTitle() {

        activityRule.launchActivity(Intent())

        val dummyTitle = "Activity Name Test"
        val dummyConfigView = ConfigView(dummyTitle)
        runBlocking(Dispatchers.Main) {
            configLiveEvents.postValue(
                ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageScreen(
                    dummyConfigView
                )
            )
        }

        onView(withId(R.id.tv_activity_trip_title)).check(matches(withText(dummyTitle)))
    }

    @Test
    fun whenConfigLiveEvent_andConfigPageLoading_shouldShowProgressBar() {

        activityRule.launchActivity(Intent())

        runBlocking(Dispatchers.Main) {
            configLiveEvents.postValue(
                ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageLoading
            )
        }

        onView(withId(R.id.pb_trip_activity_loading)).check(matches(isDisplayed()))
    }

    @Test
    fun whenClickSortByRating_shouldCallForInvokeToggleSortByRatingEvent() {

        activityRule.launchActivity(Intent())

        runBlocking(Dispatchers.Main) {
            singleLiveEvents.postValue(ReviewsViewContract.ViewState.SingleLiveEvent.ConfigurePagination)
        }

        Thread.sleep(1000)
        onView(withId(R.id.container_sort_by_rating)).perform(click())
        verify(exactly = 1) { reviewsViewModel.invokeUserInteraction(ReviewsViewContract.UserInteraction.ToggleSortByRatingEvent) }

    }

    @Test
    fun whenRecyclerViewHasContent_shouldShowViewHolder() {

        activityRule.launchActivity(Intent())

        val dummyMessage = "This is a dummy message"
        val dummyList = listOf(
            ReviewView.empty(),
            ReviewView.empty(),
            ReviewView.empty(),
            ReviewView.empty().apply {
                message = dummyMessage
            })
        runBlocking(Dispatchers.Main) {
            reviewsLiveEvents.postValue(
                ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageScreen(
                    dummyList
                )
            )
        }

        onView(RecyclerViewMatcher().atPositionOnView(3, R.id.tv_review_message, R.id.rv_reviews))
            .check(matches(withText(dummyMessage)))
    }

    @Test
    fun whenRecyclerViewHasContentAndClick_shouldCallNavigateToReviewDetails() {

        activityRule.launchActivity(Intent())

        val dummyMessage = "This is a dummy message"
        val dummyReviewView = ReviewView.empty().apply {
            message = dummyMessage
        }
        val dummyList = listOf(ReviewView.empty(), dummyReviewView)
        runBlocking(Dispatchers.Main) {
            reviewsLiveEvents.postValue(
                ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageScreen(
                    dummyList
                )
            )
        }

        onView(RecyclerViewMatcher().atPositionOnView(1, R.id.tv_review_message, R.id.rv_reviews))
            .perform(click())

        verify(exactly = 1) {
            reviewsViewModel.invokeUserInteraction(
                ReviewsViewContract.UserInteraction.NavigateToReviewDetails(
                    dummyReviewView
                )
            )
        }
    }

    @Test
    fun whenRecyclerViewHasContentAndScrollForLastPosition_shouldCallRequestReviewsEvent() {

        activityRule.launchActivity(Intent())

        val dummyList = listOf(
            ReviewView.empty(),
            ReviewView.empty(),
            ReviewView.empty(),
            ReviewView.empty(),
            ReviewView.empty(),
            ReviewView.empty(),
            ReviewView.empty()
        )
        runBlocking(Dispatchers.Main) {
            reviewsLiveEvents.postValue(
                ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageScreen(
                    dummyList
                )
            )
            singleLiveEvents.postValue(ReviewsViewContract.ViewState.SingleLiveEvent.ConfigurePagination)
        }

        Thread.sleep(2000)
        RecyclerViewMatcher().scrollToItem(6, R.id.rv_reviews)
        verify(exactly = 1) { reviewsViewModel.invokeUserInteraction(ReviewsViewContract.UserInteraction.RequestReviewsEvent) }
    }

    @Test
    fun whenNavigateToReviewDetails_shouldShowReviewDetailsFragment() {

        mockReviewDetailsViewMode()
        activityRule.launchActivity(Intent())

        runBlocking(Dispatchers.Main) {
            singleLiveEvents.postValue(
                ReviewsViewContract.ViewState.SingleLiveEvent.NavigateToReviewDetails(
                    ReviewView.empty()
                )
            )
        }


        onView(withId(R.id.container_review_details_content)).check(matches(isDisplayed()))
    }

    private fun mockReviewDetailsViewMode() {

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(ReviewDetailsViewModel::class.java) } returns reviewDetailsViewModel

        every {
            reviewDetailsViewModel.invokeUserInteraction(
                ReviewDetailsViewContract.UserInteraction.InitPageEvent(
                    ReviewView.empty()
                )
            )
        } just Runs

        every { reviewDetailsViewModel.reviewDetailsLiveEvents } returns MediatorLiveData()
    }
}
