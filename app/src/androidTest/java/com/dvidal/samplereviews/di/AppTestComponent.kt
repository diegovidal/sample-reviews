package com.dvidal.samplereviews.di

import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplereviews.core.common.BaseAppComponent
import com.dvidal.samplereviews.core.di.component.ActivityComponent
import dagger.Component
import javax.inject.Singleton

/**
 * @author diegovidal on 2020-02-14.
 */
@Singleton
@Component(modules = [
    ViewModelTestModule::class
])
interface AppTestComponent: BaseAppComponent {

    override fun activityComponent(): ActivityComponent.Builder
    override val viewModelFactor: ViewModelProvider.Factory
}