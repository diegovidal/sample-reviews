package com.dvidal.samplereviews.features.details

/**
 * @author diegovidal on 2020-02-16.
 */
sealed class ReviewDetailsViewContract {

    sealed class ViewModel: ReviewDetailsViewContract(){}

    sealed class UserInteraction: ReviewDetailsViewContract(){}

    sealed class ViewState: ReviewDetailsViewContract() {}
}