package com.dvidal.samplereviews.features.list.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.common.BaseFragment
import com.dvidal.samplereviews.features.MainActivity
import com.dvidal.samplereviews.features.list.presentation.adapter.ReviewViewHolder
import com.dvidal.samplereviews.features.list.presentation.adapter.ReviewsAdapter
import kotlinx.android.synthetic.main.fragment_reviews.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-14.
 */
class ReviewsFragment: BaseFragment(), ReviewViewHolder.ReviewViewHolderListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var reviewsAdapter: ReviewsAdapter

    private val viewModel: ReviewsViewContract.ViewModel by lazy {
        ViewModelProviders.of(requireActivity(), viewModelFactory).get(ReviewsViewModel::class.java)
    }

    override fun layoutRes() = R.layout.fragment_reviews
    override fun injectDagger() = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        viewModel.configLiveEvents.observe(viewLifecycleOwner, Observer (::renderConfigLiveEvents))
        viewModel.reviewsLiveEvents.observe(viewLifecycleOwner, Observer(::renderReviewsLiveEvents))
        viewModel.singleLiveEvents.observe(viewLifecycleOwner, Observer (::configureSingleLiveEvent))

        viewModel.invokeUserInteraction(ReviewsViewContract.UserInteraction.InitPageEvent)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.updateActionBarTitle(R.string.reviews_title)
    }

    private fun renderReviewsLiveEvents(reviewsLiveEvent: ReviewsViewContract.ViewState.ReviewsLiveEvent) {

        when(reviewsLiveEvent) {
            is ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageScreen -> {

                reviewsAdapter.updateDataSet(reviewsLiveEvent.list)
                shouldShowContentPage(true)
            }
            ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageLoading -> shouldShowContentPage(false)
        }
    }

    private fun renderConfigLiveEvents(configLiveEvent: ReviewsViewContract.ViewState.ConfigLiveEvent) {

        when(configLiveEvent) {
            is ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageScreen -> {}
            ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageLoading -> {}
        }
    }

    private fun configureSingleLiveEvent(singleLiveEvent: ReviewsViewContract.ViewState.SingleLiveEvent) {

        when(singleLiveEvent) {
            is ReviewsViewContract.ViewState.SingleLiveEvent.ErrorWarning ->
                Toast.makeText(requireContext(), singleLiveEvent.throwable.message, Toast.LENGTH_SHORT).show()
            is ReviewsViewContract.ViewState.SingleLiveEvent.NavigateToReviewDetails ->
                (activity as? MainActivity)?.navigator?.navigateToReviewDetailsFragment(requireActivity(), singleLiveEvent.reviewDetails)
            ReviewsViewContract.ViewState.SingleLiveEvent.ConfigurePagination -> configurePagination()
        }
    }

    private fun shouldShowContentPage(isVisible: Boolean) {

        container_reviews_content.isVisible = isVisible
        pb_reviews.isVisible = !isVisible
    }

    private fun configureRecyclerView() {

        reviewsAdapter.configureListener(this)
        rv_reviews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_reviews?.setHasFixedSize(true)
        rv_reviews.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_reviews.adapter = reviewsAdapter
    }

    private fun configurePagination() {

        nsv_reviews_content.viewTreeObserver
            .addOnScrollChangedListener {
                if (!nsv_reviews_content.canScrollVertically(1)) {
                    viewModel.invokeUserInteraction(ReviewsViewContract.UserInteraction.RequestReviewsEvent)
                }
            }
    }

    companion object {

        const val REVIEWS_FRAGMENT = "REVIEWS_FRAGMENT"

        fun newInstance() = ReviewsFragment()
    }

    override fun onClickReview(reviewView: ReviewView) {
        viewModel.invokeUserInteraction(ReviewsViewContract.UserInteraction.NavigateToReviewDetails(reviewView))
    }
}