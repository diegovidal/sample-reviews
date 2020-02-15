package com.dvidal.samplereviews.features.list.data.local.config

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author diegovidal on 2020-02-14.
 */

@Entity
data class ConfigDto(
    @PrimaryKey val id: Long = -1,
    val activityName: String = "",
    val offsetPage: Int = 0,
    val numReviews: Int = 0,
    val averageRating: Double = 0.0,
    val isDescendingOrderRating: Boolean = true
)