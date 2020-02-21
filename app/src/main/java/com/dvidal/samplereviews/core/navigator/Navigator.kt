package com.dvidal.samplereviews.core.navigator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.dvidal.samplereviews.R
import com.dvidal.samplereviews.features.details.ReviewDetailsFragment
import com.dvidal.samplereviews.features.details.ReviewDetailsFragment.Companion.REVIEW_VIEW_EXTRA
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import com.dvidal.samplereviews.features.list.presentation.ReviewsFragment
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-16.
 */
class Navigator @Inject constructor() {

    fun navigateTo(activity: AppCompatActivity, bundle: Bundle?) {

        when(bundle?.getString(FRAGMENT_TYPE)) {

            ReviewDetailsFragment.REVIEW_DETAILS_FRAGMENT -> {
                val reviewView = bundle.getParcelable(REVIEW_VIEW_EXTRA) as? ReviewView
                navigateToReviewDetailsFragment(activity, reviewView)
            }
            ReviewsFragment.REVIEWS_FRAGMENT -> navigateToReviewsFragment(activity)
            else -> navigateToReviewsFragment(activity)
        }
    }

    private fun navigateToReviewsFragment(activity: AppCompatActivity) {

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.frame_content, ReviewsFragment.newInstance())
            .commit()
    }

    fun navigateToReviewDetailsFragment(activity: FragmentActivity?, reviewView: ReviewView?) {

        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_content, ReviewDetailsFragment.newInstance(reviewView))
            ?.addToBackStack(ReviewDetailsFragment.REVIEW_DETAILS_FRAGMENT)
            ?.commit()
    }

    companion object {

        const val FRAGMENT_TYPE = "FRAGMENT_TYPE"
    }
}