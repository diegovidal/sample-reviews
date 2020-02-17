package com.dvidal.samplereviews.features.list.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.common.BaseFragment
import com.dvidal.samplereviews.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplereviews.features.MainActivity
import kotlinx.android.synthetic.main.fragment_reviews.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-14.
 */
class ReviewsFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ReviewsViewContract.ViewModel by lazy {
        viewModelFactory.get<ReviewsViewModel>(requireActivity()) as ReviewsViewContract.ViewModel
    }

    override fun layoutRes() = R.layout.fragment_reviews
    override fun injectDagger() = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.configLiveEvents.observe(viewLifecycleOwner, Observer {
            Timber.i("Info Config")
        })

        viewModel.reviewsLiveEvents.observe(viewLifecycleOwner, Observer {
            Timber.i("Info Reviews")
        })

        viewModel.singleLiveEvents.observe(viewLifecycleOwner, Observer {
            Timber.i("Single Live Events")
        })

        bt_load.setOnClickListener {
            viewModel.invokeUserInteraction(ReviewsViewContract.UserInteraction.InitPageEvent)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.updateActionBarTitle(R.string.reviews_title)
    }

    companion object {

        const val REVIEWS_FRAGMENT = "REVIEWS_FRAGMENT"

        fun newInstance() = ReviewsFragment()
    }
}