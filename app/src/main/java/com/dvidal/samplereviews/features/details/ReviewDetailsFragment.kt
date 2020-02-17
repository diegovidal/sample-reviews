package com.dvidal.samplereviews.features.details

import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.common.BaseFragment
import com.dvidal.samplereviews.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplereviews.features.MainActivity
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
class ReviewDetailsFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ReviewDetailsViewContract.ViewModel by lazy {
        viewModelFactory.get<ReviewDetailsViewModel>(requireActivity()) as ReviewDetailsViewContract.ViewModel
    }

    override fun layoutRes() = R.layout.fragment_review_details
    override fun injectDagger() = component.inject(this)

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.updateActionBarTitle(R.string.review_details_title)
    }

    companion object {

        const val REVIEW_DETAILS_FRAGMENT = "REVIEW_DETAILS_FRAGMENT"

        fun newInstance() = ReviewDetailsFragment()
    }
}