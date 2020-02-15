package com.dvidal.samplereviews.features.list.presentation

/**
 * @author diegovidal on 2020-02-15.
 */
data class ConfigView(
    val activityName: String = "",
    val numReviews: Int = 0,
    val averageRating: Double = 0.0,
    val isDescendingOrderRating: Boolean = true
)