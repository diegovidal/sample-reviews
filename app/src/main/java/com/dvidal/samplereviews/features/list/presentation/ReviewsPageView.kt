package com.dvidal.samplereviews.features.list.presentation

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

data class ReviewView(

    val id: Long,
    val author: AuthorView,
    val title: String,
    val message: String,
    val enjoyment: String,
    val isAnonymous: Boolean,
    val rating: Int,
    val created: String,
    val language: String,
    val travelerType: String
)

data class AuthorView(

    val fullName: String,
    val country: String,
    val city: String,
    val photo: String
)

data class PaginationView(

    val limit: Int,
    val offset: Int
)