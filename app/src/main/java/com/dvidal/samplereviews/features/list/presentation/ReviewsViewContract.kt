package com.dvidal.samplereviews.features.list.presentation

/**
 * @author diegovidal on 2020-02-16.
 */
sealed class ReviewsViewContract {

    sealed class ViewModel: ReviewsViewContract(){}

    sealed class UserInteraction: ReviewsViewContract(){}

    sealed class ViewState: ReviewsViewContract() {}
}