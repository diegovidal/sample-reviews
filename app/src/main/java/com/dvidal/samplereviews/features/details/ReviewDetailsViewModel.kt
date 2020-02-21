package com.dvidal.samplereviews.features.details

import androidx.lifecycle.MediatorLiveData
import com.dvidal.samplereviews.core.common.BaseCoroutineDispatcher
import com.dvidal.samplereviews.core.common.BaseViewModel
import com.dvidal.samplereviews.core.common.notLet
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
open class ReviewDetailsViewModel @Inject constructor(
    private val dispatcher: BaseCoroutineDispatcher
) : BaseViewModel(), ReviewDetailsViewContract.ViewModel {

    override val reviewDetailsLiveEvents = MediatorLiveData<ReviewDetailsViewContract.ViewState>()

    override fun invokeUserInteraction(userInteraction: ReviewDetailsViewContract.UserInteraction) {

        reviewDetailsLiveEvents.notLet {
            if (userInteraction is ReviewDetailsViewContract.UserInteraction.InitPageEvent) {
                reviewDetailsLiveEvents.postValue(
                    ReviewDetailsViewContract.ViewState.ReviewDetailsPageScreen(
                        userInteraction.reviewView
                    )
                )
            }
        }
    }
}