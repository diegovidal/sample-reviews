package com.dvidal.samplereviews.features.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.common.BaseFragment
import com.dvidal.samplereviews.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplereviews.core.extension.dateToRead
import com.dvidal.samplereviews.features.MainActivity
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import kotlinx.android.synthetic.main.fragment_review_details.*
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
class ReviewDetailsFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ReviewDetailsViewContract.ViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(ReviewDetailsViewModel::class.java)
    }

    override fun layoutRes() = R.layout.fragment_review_details
    override fun injectDagger() = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<ReviewView>(REVIEW_VIEW_EXTRA)?.let {
            viewModel.invokeUserInteraction(ReviewDetailsViewContract.UserInteraction.InitPageEvent(it))
        }

        viewModel.reviewsLiveEvents.observe(viewLifecycleOwner, Observer (::renderReviewDetailsLiveEvents))
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.updateActionBarTitle(R.string.review_details_title)
        (activity as? MainActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? MainActivity)?.supportActionBar?.elevation = 4f
    }

    private fun renderReviewDetailsLiveEvents(viewState: ReviewDetailsViewContract.ViewState) {
        if (viewState is ReviewDetailsViewContract.ViewState.ReviewDetailsPageScreen){

            val reviewView = viewState.reviewView
            val city = if (reviewView.author.city.isNotBlank()) ", ${reviewView.author.city}" else ""

            Glide.with(requireContext())
                .load(reviewView.author.photo)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.author_photo_placeholder)
                .into(iv_author_photo)

            tv_review_date.text = reviewView.created.dateToRead() ?: reviewView.created
            tv_author_name.text = reviewView.author.fullName
            tv_country.text = getString(R.string.label_country_city, reviewView.author.country, city)
            rb_activity_trip_rate.rating = reviewView.rating.toFloat()
            tv_title_message.text = if (reviewView.title.isNotBlank()) reviewView.title else getString(R.string.label_no_title)
            tv_review_message.text = reviewView.message
            tv_enjoyment.text = reviewView.enjoyment
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