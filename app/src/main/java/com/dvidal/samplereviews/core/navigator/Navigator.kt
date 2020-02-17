package com.dvidal.samplereviews.core.navigator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.features.details.ReviewDetailsFragment
import com.dvidal.samplereviews.features.list.presentation.ReviewsFragment
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
class Navigator @Inject constructor() {

    fun navigateTo(activity: AppCompatActivity, bundle: Bundle?) {

        when(bundle?.getString(FRAGMENT_TYPE)) {

            ReviewDetailsFragment.REVIEW_DETAILS_FRAGMENT -> navigateToReviewDetailsFragment(activity)
            ReviewsFragment.REVIEWS_FRAGMENT -> navigateToReviewsFragment(activity)
            else -> navigateToReviewDetailsFragment(activity)
        }
    }

    fun navigateToReviewsFragment(activity: AppCompatActivity) {

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_content, ReviewsFragment.newInstance())
            .commit()
    }

    fun navigateToReviewDetailsFragment(activity: AppCompatActivity) {

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_content, ReviewDetailsFragment.newInstance())
            .addToBackStack(ReviewDetailsFragment.REVIEW_DETAILS_FRAGMENT)
            .commit()
    }

    companion object {

        const val FRAGMENT_TYPE = "FRAGMENT_TYPE"
    }
}