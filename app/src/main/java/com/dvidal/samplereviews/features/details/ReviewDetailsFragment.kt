package com.dvidal.samplereviews.features.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.common.BaseFragment
import com.dvidal.samplereviews.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplereviews.features.MainActivity
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
class ReviewDetailsFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ReviewDetailsViewContract.ViewModel by lazy {
        ViewModelProviders.of(this).get(ReviewDetailsViewModel::class.java)
    }

    override fun layoutRes() = R.layout.fragment_review_details
    override fun injectDagger() = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        arguments?.getParcelable<ReviewView>(REVIEW_VIEW_EXTRA)?.let {
//            viewModel.invokeUserInteraction(ReviewDetailsViewContract.UserInteraction.InitPageEvent(it))
//        }
//
//        viewModel.reviewsLiveEvents.observe(viewLifecycleOwner, Observer (::renderReviewDetailsLiveEvents))
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.updateActionBarTitle(R.string.review_details_title)
    }

    private fun renderReviewDetailsLiveEvents(viewState: ReviewDetailsViewContract.ViewState) {
        if (viewState is ReviewDetailsViewContract.ViewState.ReviewDetailsPageScreen){

            val reviewView = viewState.reviewView
        }
    }

    companion object {

        const val REVIEW_DETAILS_FRAGMENT = "REVIEW_DETAILS_FRAGMENT"
        private const val REVIEW_VIEW_EXTRA = "REVIEW_VIEW_EXTRA"

        fun newInstance(reviewView: ReviewView?) = ReviewDetailsFragment().apply {

            val bundle = Bundle()
            bundle.putParcelable(REVIEW_VIEW_EXTRA, reviewView)
            this.arguments = bundle
        }
    }
}