package com.dvidal.samplereviews.features.list.presentation

import android.os.Parcelable
import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import kotlinx.android.parcel.Parcelize

/**
 * @author diegovidal on 2020-02-15.
 */

data class ReviewsPageView(

    val reviews: List<ReviewView>,
    val totalCount: Int,
    val averageRating: Double,
    val pagination: PaginationView
) {

    companion object {
        fun empty() = ReviewsPageView(
            reviews = emptyList(),
            totalCount = 0,
            averageRating = 2.0,
            pagination = PaginationView(
                limit = 5,
                offset = 0
            )
        )
    }
}

@Parcelize
data class ReviewView(
    val id: Long,
    var author: AuthorView,
    val title: String,
    var message: String,
    val enjoyment: String,
    val isAnonymous: Boolean,
    val rating: Int,
    val created: String,
    val travelerType: String
): Parcelable {

    companion object {

        fun empty() = ReviewView(
            id = -1,
            author = AuthorView.empty(),
            title = "",
            message = "",
            enjoyment = "",
            isAnonymous = false,
            rating = 0,
            created = "",
            travelerType = ""
        )
    }
}

@Parcelize
data class AuthorView(

    var fullName: String,
    val country: String,
    val city: String,
    val photo: String
): Parcelable {

    companion object {

        fun empty() = AuthorView(
            fullName = "",
            country = "",
            city = "",
            photo = ""
        )
    }
}

data class PaginationView(

    val limit: Int,
    val offset: Int
)