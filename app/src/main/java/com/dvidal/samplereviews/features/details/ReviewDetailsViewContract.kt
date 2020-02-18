package com.dvidal.samplereviews.features.details

import androidx.lifecycle.MediatorLiveData
import com.dvidal.samplereviews.features.list.presentation.ReviewView

/**
 * @author diegovidal on 2020-02-16.
 */
sealed class ReviewDetailsViewContract {

    interface ViewModel {

        val reviewsLiveEvents: MediatorLiveData<ViewState>
        fun invokeUserInteraction(userInteraction: UserInteraction)
    }

    sealed class UserInteraction: ReviewDetailsViewContract(){

        data class InitPageEvent(val reviewView: ReviewView) : UserInteraction()
    }

    sealed class ViewState: ReviewDetailsViewContract() {

        data class ReviewDetailsPageScreen(val reviewView: ReviewView): ViewState()
    }
}