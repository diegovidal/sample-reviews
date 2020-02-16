package com.dvidal.samplereviews.features.list.data.local.config

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvidal.samplereviews.features.list.presentation.ConfigView

/**
 * @author diegovidal on 2020-02-14.
 */

@Entity
data class ConfigDto(
    @PrimaryKey val id: Long = -1,
    var activityName: String = "",
    val offsetPage: Int = 0,
    val numReviews: Int = 0,
    val averageRating: Double = 0.0,
    val isDescendingOrderRating: Boolean = true
) {

    fun mapperToConfigView() = ConfigView(
        activityName = this.activityName,
        numReviews = this.numReviews,
        averageRating = this.averageRating,
        isDescendingOrderRating = this.isDescendingOrderRating
    )
}