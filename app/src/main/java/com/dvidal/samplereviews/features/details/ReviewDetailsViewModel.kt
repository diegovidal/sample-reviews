package com.dvidal.samplereviews.features.details

import androidx.lifecycle.MediatorLiveData
import com.dvidal.samplereviews.core.common.BaseCoroutineDispatcher
import com.dvidal.samplereviews.core.common.BaseViewModel
import com.dvidal.samplereviews.core.common.notLet
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
class ReviewDetailsViewModel @Inject constructor(
    private val dispatcher: BaseCoroutineDispatcher
) : BaseViewModel(), ReviewDetailsViewContract.ViewModel {

    override val reviewsLiveEvents = MediatorLiveData<ReviewDetailsViewContract.ViewState>()

    override fun invokeUserInteraction(userInteraction: ReviewDetailsViewContract.UserInteraction) {

        reviewsLiveEvents.notLet {
            if (userInteraction is ReviewDetailsViewContract.UserInteraction.InitPageEvent) {
                reviewsLiveEvents.postValue(
                    ReviewDetailsViewContract.ViewState.ReviewDetailsPageScreen(
                        userInteraction.reviewView
                    )
                )
            }
        }
    }
}