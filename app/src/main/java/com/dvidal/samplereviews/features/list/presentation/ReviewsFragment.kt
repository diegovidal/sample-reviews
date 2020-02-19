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
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.common.BaseFragment
import com.dvidal.samplereviews.features.MainActivity
import com.dvidal.samplereviews.features.list.presentation.adapter.ReviewViewHolder
import com.dvidal.samplereviews.features.list.presentation.adapter.ReviewsAdapter
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.android.synthetic.main.view_trip_activity_information.*
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
        configureSwipeRefreshLayout()

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
                shouldShowReviewsContentPage(true)
            }
            ReviewsViewContract.ViewState.ReviewsLiveEvent.ReviewsPageLoading -> shouldShowReviewsContentPage(false)
        }
    }

    private fun renderConfigLiveEvents(configLiveEvent: ReviewsViewContract.ViewState.ConfigLiveEvent) {

        when(configLiveEvent) {
            is ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageScreen -> {

                tv_activity_trip_title.text = configLiveEvent.config.activityName
                tv_activity_trip_rate_average.text = getString(R.string.label_activity_trip_average_rate, configLiveEvent.config.averageRating.toFloat())
                tv_activity_trip_total_reviews.text = getString(R.string.label_activity_trip_total_reviews, configLiveEvent.config.numReviews)
                rb_activity_trip_rate.rating = configLiveEvent.config.averageRating.toFloat()
                shouldShowActivityTripContentPage(true)
            }
            ReviewsViewContract.ViewState.ConfigLiveEvent.ConfigPageLoading -> shouldShowActivityTripContentPage(false)
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

    private fun shouldShowReviewsContentPage(isVisible: Boolean) {

        container_reviews_content.isVisible = isVisible
        srl_reviews.isRefreshing = !isVisible
    }

    private fun shouldShowActivityTripContentPage(isVisible: Boolean) {

        container_trip_activity_content.isVisible = isVisible
        pb_trip_activity_loading.isVisible = !isVisible
    }

    private fun configureRecyclerView() {

        reviewsAdapter.configureListener(this)
        rv_reviews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rv_reviews?.setHasFixedSize(true)
        rv_reviews.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_reviews.adapter = reviewsAdapter
    }

    private fun configureSwipeRefreshLayout() {

        srl_reviews.setOnRefreshListener { viewModel.invokeUserInteraction(ReviewsViewContract.UserInteraction.RefreshPageEvent) }
    }

    private fun configurePagination() {

        srl_reviews.isRefreshing = false

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