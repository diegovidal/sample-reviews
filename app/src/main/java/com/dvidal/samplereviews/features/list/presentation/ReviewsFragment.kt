package com.dvidal.samplereviews.features.list.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.core.common.BaseFragment
import com.dvidal.samplereviews.core.di.module.viewmodel.ViewModelFactory
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
    }
}