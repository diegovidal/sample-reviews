package com.dvidal.samplereviews.core.di.component

import com.dvidal.samplereviews.features.MainActivity
import com.dvidal.samplereviews.features.details.ReviewDetailsFragment
import com.dvidal.samplereviews.features.list.presentation.ReviewsFragment
import dagger.Subcomponent

/**
 * @author diegovidal on 2020-02-16.
 */

@Subcomponent
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(reviewsFragment: ReviewsFragment)
    fun inject(reviewDetailsFragment: ReviewDetailsFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): ActivityComponent
    }
}