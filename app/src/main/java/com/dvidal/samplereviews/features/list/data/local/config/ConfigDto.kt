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
    val averageRating: Float = 0.0F,
    val isDescendingOrder: Boolean = true
)