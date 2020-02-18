package com.dvidal.samplereviews.core.common

import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplereviews.core.di.component.ActivityComponent

/**
 * @author diegovidal on 2020-02-18.
 */
interface BaseAppComponent {

    fun activityComponent(): ActivityComponent.Builder
    val viewModelFactor: ViewModelProvider.Factory
}