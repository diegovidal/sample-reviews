package com.dvidal.samplereviews.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplereviews.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplereviews.core.di.module.viewmodel.ViewModelKey
import com.dvidal.samplereviews.features.details.ReviewDetailsViewModel
import com.dvidal.samplereviews.features.list.presentation.ReviewsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.mockk.mockk
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-02-17.
 */
@Module
class ViewModelTestModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(): ViewModelProvider.Factory = mockk()
}
