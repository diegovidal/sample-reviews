package com.dvidal.samplereviews.features

import android.content.Intent
import androidx.lifecycle.MediatorLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dvidal.samplereviews.BaseEspressoTest
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.navigator.Navigator.Companion.FRAGMENT_TYPE
import com.dvidal.samplereviews.features.details.ReviewDetailsFragment
import com.dvidal.samplereviews.features.details.ReviewDetailsFragment.Companion.REVIEW_DETAILS_FRAGMENT
import com.dvidal.samplereviews.features.details.ReviewDetailsViewContract
import com.dvidal.samplereviews.features.details.ReviewDetailsViewModel
import com.dvidal.samplereviews.features.list.presentation.AuthorView
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author diegovidal on 2020-02-21.
 */

@RunWith(AndroidJUnit4::class)
class ReviewDetailsFragmentTest : BaseEspressoTest() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val reviewDetailsViewModel = mockk<ReviewDetailsViewModel>(relaxUnitFun = true)
    private val reviewDetailsLiveEvents = MediatorLiveData<ReviewDetailsViewContract.ViewState>()

    private val reviewView = ReviewView.empty().apply {
        author = AuthorView.empty().apply {
            fullName = "Mr. Test"
        }
        message = "This is a test"
    }

    @Before
    fun setup() {

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(ReviewDetailsViewModel::class.java) } returns reviewDetailsViewModel

        every {
            reviewDetailsViewModel.invokeUserInteraction(
                ReviewDetailsViewContract.UserInteraction.InitPageEvent(
                    ReviewView.empty()
                )
            )
        } just Runs

        every { reviewDetailsViewModel.reviewDetailsLiveEvents } returns reviewDetailsLiveEvents
    }

    @Test
    fun whenShowReviewDetailsFragment_shouldCallInitPageEvent() {

        activityRule.launchActivity(injectIntent())

        verify(exactly = 1) { reviewDetailsViewModel.invokeUserInteraction(ReviewDetailsViewContract.UserInteraction.InitPageEvent(reviewView)) }
    }

    @Test
    fun whenConfigLiveEvent_andConfigPageScreen_shouldShowTitle() {

        activityRule.launchActivity(injectIntent())

        runBlocking(Dispatchers.Main) {
            reviewDetailsLiveEvents.postValue(
                ReviewDetailsViewContract.ViewState.ReviewDetailsPageScreen(reviewView)
            )
        }

        onView(ViewMatchers.withId(R.id.tv_author_name))
            .check(ViewAssertions.matches(ViewMatchers.withText(reviewView.author.fullName)))

        onView(ViewMatchers.withId(R.id.tv_review_message))
            .check(ViewAssertions.matches(ViewMatchers.withText(reviewView.message)))
    }

    private fun injectIntent(): Intent {

        return Intent().apply {
            putExtra(FRAGMENT_TYPE, REVIEW_DETAILS_FRAGMENT)
            putExtra(ReviewDetailsFragment.REVIEW_VIEW_EXTRA, reviewView)
        }
    }
}