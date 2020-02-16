package com.dvidal.samplereviews.features.list.data.local.reviews

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dvidal.samplereviews.features.list.presentation.AuthorView
import com.dvidal.samplereviews.features.list.presentation.ReviewView

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
    val isAnonymous: Boolean = false,
    val rating: Int = 0,
    val createdDate: String = "",
    val travelerType: String = ""
) {

    fun mapperToReviewView() = ReviewView(
        author = AuthorView(
            fullName = this.authorName,
            country = this.authorCountry,
            city = this.authorCity,
            photo = this.authorPhoto
        ),
        title = this.title,
        message = this.message,
        enjoyment = this.enjoyment,
        isAnonymous = this.isAnonymous,
        rating = this.rating,
        created = this.createdDate,
        travelerType = this.travelerType
    )
}