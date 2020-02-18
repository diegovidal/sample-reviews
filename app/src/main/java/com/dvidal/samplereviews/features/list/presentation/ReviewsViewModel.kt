package com.dvidal.samplereviews.features.list.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.dvidal.samplereviews.core.common.BaseCoroutineDispatcher
import com.dvidal.samplereviews.core.common.BaseViewModel
import com.dvidal.samplereviews.core.common.SingleLiveEvent
import com.dvidal.samplereviews.core.common.UseCase
import com.dvidal.samplereviews.features.list.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
open class ReviewsViewModel @Inject constructor(
    private val dispatcher: BaseCoroutineDispatcher,
    private val fetchConfigUseCase: FetchConfigUseCase,
    private val fetchReviewsUseCase: FetchReviewsUseCase,
    private val refreshPageUseCase: RefreshPageUseCase,
    private val requestReviewsUseCase: RequestReviewsUseCase,
    private val toggleSortByRatingUseCase: ToggleSortByRatingUseCase
) : BaseViewModel(), ReviewsViewContract.ViewModel {

    @VisibleForTesting
    val userInteraction = SingleLiveEvent<ReviewsViewContract.UserInteraction>()

    override val singleLiveEvents =
        SingleLiveEvent<ReviewsViewContract.ViewState.SingleLiveEvent>().apply {

            addSource(failure) {
                postValue(ReviewsViewContract.ViewState.SingleLiveEvent.ErrorWarning(it))
            }

            addSource(userInteraction) {
                handleUserInteraction(it)
            }
        }

    override val reviewsLiveEvents =
        MediatorLiveData<ReviewsViewContract.ViewState.ReviewsLiveEvent>()

    override val configLiveEvents =
        MediatorLiveData<ReviewsViewContract.ViewState.ConfigLiveEvent>()

    override fun invokeUserInteraction(userInteraction: ReviewsViewContract.UserInteraction) {
        this.userInteraction.postValue(userInteraction)
    }

    private suspend fun fetchReviewsUseCase() {

        reviewsLiveEvents.postValue(ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageLoading)
        fetchReviewsUseCase.invoke(UseCase.None()).also {
            it.either(::handleFailure) { liveData ->
                reviewsLiveEvents.apply {

                    viewModelScope.launch(Dispatchers.Main) {
                        addSource(liveData) { list ->

                            val listConverted = list.map { it.mapperToReviewView() }
                            postValue(ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageScreen(listConverted))
                        }
                    }
                }
            }
        }
    }

    private suspend fun fetchConfigUseCase() {

        configLiveEvents.postValue(ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageLoading)
        fetchConfigUseCase.invoke(UseCase.None()).also {
            it.either(::handleFailure) { liveData ->
                configLiveEvents.apply {

                    viewModelScope.launch(Dispatchers.Main) {
                        addSource(liveData) { configDto ->

                            configDto?.mapperToConfigView()?.let { configView ->
                                postValue(
                                    ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageScreen(
                                        configView
                                    )
                                )
                            } ?: invokeUserInteraction(ReviewsViewContract.UserInteraction.RequestReviewsEvent)
                        }
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun handleUserInteraction(userInteraction: ReviewsViewContract.UserInteraction) {

        viewModelScope.launch(dispatcher.IO()) {
            when (userInteraction) {
                is ReviewsViewContract.UserInteraction.InitPageEvent -> {

                    fetchConfigUseCase()
                    fetchReviewsUseCase()
                }
                is ReviewsViewContract.UserInteraction.RequestReviewsEvent -> {

                    requestReviewsUseCase.invoke(UseCase.None()).also {
                        it.either(::handleFailure) {}
                    }
                }
                is ReviewsViewContract.UserInteraction.ToggleSortByRatingEvent -> {

                    toggleSortByRatingUseCase.invoke(UseCase.None()).also {
                        it.either(::handleFailure) {}
                    }
                }
                is ReviewsViewContract.UserInteraction.RefreshPageEvent -> {

                    refreshPageUseCase.invoke(UseCase.None()).also {
                        it.either(::handleFailure) {}
                    }
                }
                is ReviewsViewContract.UserInteraction.NavigateToReviewDetails -> {

                    withContext(dispatcher.Main()) {
                        singleLiveEvents.postValue(
                            ReviewsViewContract.ViewState.SingleLiveEvent.NavigateToReviewDetails(
                                userInteraction.reviewDetails
                            )
                        )
                    }
                }
            }
        }
    }
}