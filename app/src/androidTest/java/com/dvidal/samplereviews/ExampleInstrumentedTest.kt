package com.dvidal.samplereviews

import android.content.Intent
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dvidal.samplereviews.core.common.SingleLiveEvent
import com.dvidal.samplereviews.features.MainActivity
import com.dvidal.samplereviews.features.list.presentation.ReviewsViewContract
import com.dvidal.samplereviews.features.list.presentation.ReviewsViewModel
import io.mockk.every
import io.mockk.mockk

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest: BaseEspressoTest() {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val viewModel = mockk<ReviewsViewModel>(relaxUnitFun = true)

    private val configLiveEvents = MediatorLiveData<ReviewsViewContract.ViewState.ConfigLiveEvent>()
    private val reviewsLiveEvents = MediatorLiveData<ReviewsViewContract.ViewState.ReviewsLiveEvent>()
    private val singleLiveEvents = SingleLiveEvent<ReviewsViewContract.ViewState.SingleLiveEvent>()

    @Before
    fun setup() {

    }

    @Test
    fun useAppContext() {
        // Context of the app under test.

        every { viewModel.configLiveEvents } returns configLiveEvents
        every { viewModel.reviewsLiveEvents } returns reviewsLiveEvents
        every { viewModel.singleLiveEvents } returns singleLiveEvents

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(ReviewsViewModel::class.java) } returns viewModel

        activityRule.launchActivity(Intent())

        onView(withId(R.id.bt_load)).perform(click())
    }
}
