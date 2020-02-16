package com.dvidal.samplereviews.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplereviews.features.details.ReviewDetailsViewModel
import com.dvidal.samplereviews.features.list.presentation.ReviewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author diegovidal on 2020-02-16.
 */

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ReviewsViewModel::class)
    abstract fun bindReviewsViewModel(viewModel: ReviewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewDetailsViewModel::class)
    abstract fun bindReviewDetailsViewModel(viewModel: ReviewDetailsViewModel): ViewModel
}