package com.dvidal.samplereviews.features.list.data.local.reviews

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author diegovidal on 2020-02-14.
 */

@Entity
data class ReviewDto(
    @PrimaryKey val id: Long = -1,
    val title: String = "",
    val message: String = "",
    val authorName: String = "",
    val authorCountry: String = "",
    val authorCity: String = "",
    val authorPhoto: String = "",
    val enjoyment: String = "",
    val rating: Int = 0,
    val createdDate: String = "",
    val travelerType: String = ""
)