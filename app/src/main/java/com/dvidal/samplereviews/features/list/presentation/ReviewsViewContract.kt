package com.dvidal.samplereviews.features.list.presentation

import androidx.lifecycle.MediatorLiveData
import com.dvidal.samplereviews.core.common.SingleLiveEvent

/**
 * @author diegovidal on 2020-02-16.
 */
sealed class ReviewsViewContract {

    interface ViewModel {

        val reviewsLiveEvents: MediatorLiveData<ViewState.ReviewsLiveEvent>
        val configLiveEvents: MediatorLiveData<ViewState.ConfigLiveEvent>

        val singleLiveEvents: SingleLiveEvent<ViewState.SingleLiveEvent>

        fun invokeUserInteraction(userInteraction: UserInteraction)
    }

    sealed class UserInteraction: ReviewsViewContract(){

        object InitPageEvent: UserInteraction()
        object RequestReviewsEvent: UserInteraction()
        object ToggleSortByRatingEvent: UserInteraction()
        object RefreshPageEvent: UserInteraction()
        data class NavigateToReviewDetails(val reviewDetails: ReviewView): UserInteraction()
    }

    sealed class ViewState: ReviewsViewContract() {

        sealed class ReviewsLiveEvent {

            data class ReviewsPageScreen(val list: List<ReviewView>): ReviewsLiveEvent()
            object ReviewsPageLoading: ReviewsLiveEvent()
        }

        sealed class ConfigLiveEvent {

            data class ConfigPageScreen(val config: ConfigView): ConfigLiveEvent()
            object ConfigPageLoading: ConfigLiveEvent()
        }

        sealed class SingleLiveEvent: ViewState() {

            data class ErrorWarning(val throwable: Throwable): SingleLiveEvent()
            data class NavigateToReviewDetails(val reviewDetails: ReviewView): SingleLiveEvent()
        }
    }
}