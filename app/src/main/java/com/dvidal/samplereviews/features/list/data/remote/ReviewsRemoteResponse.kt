package com.dvidal.samplereviews.features.list.data.remote

import com.dvidal.samplereviews.features.list.data.local.config.ConfigDto
import com.dvidal.samplereviews.features.list.data.local.reviews.ReviewDto
import com.dvidal.samplereviews.features.list.presentation.AuthorView
import com.dvidal.samplereviews.features.list.presentation.PaginationView
import com.dvidal.samplereviews.features.list.presentation.ReviewView
import com.dvidal.samplereviews.features.list.presentation.ReviewsPageView
import com.squareup.moshi.Json

/**
 * @author diegovidal on 2020-02-15.
 */


data class ReviewsRemoteResponse(

    @Json(name = "reviews") var reviews: List<ReviewRemote>,
    @Json(name = "totalCount") val totalCount: Int,
    @Json(name = "averageRating") val averageRating: Double,
    @Json(name = "pagination") val pagination: PaginationRemote
) {

    fun mapperToReviewsPageView() = ReviewsPageView(
        reviews = this.reviews.map { it.mapperToReviewView() },
        totalCount = this.totalCount,
        averageRating = this.averageRating,
        pagination = this.pagination.mapperToPaginationView()
    )

    fun mapperToConfigDto() = ConfigDto(
        numReviews = this.totalCount,
        averageRating = this.averageRating
    )

    companion object {
        fun empty() = ReviewsRemoteResponse(
            reviews = emptyList(),
            totalCount = 0,
            averageRating = 2.0,
            pagination = PaginationRemote(
                limit = 5,
                offset = 0
            )
        )
    }
}

data class ReviewRemote(

    @Json(name = "id") val id: Long,
    @Json(name = "author") val author: AuthorRemote,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "enjoyment") val enjoyment: String,
    @Json(name = "isAnonymous") val isAnonymous: Boolean,
    @Json(name = "rating") val rating: Int,
    @Json(name = "created") val created: String,
    @Json(name = "language") val language: String,
    @Json(name = "travelerType") val travelerType: String
) {

    fun mapperToReviewView() = ReviewView(
        id = this.id,
        author = this.author.mapperToAuthorView(),
        title = this.title,
        message = this.message,
        enjoyment = this.enjoyment,
        isAnonymous = this.isAnonymous,
        rating = this.rating,
        created = this.created,
        language = this.language,
        travelerType = this.travelerType
    )

    fun mapperToReviewDto() = ReviewDto(
        id = this.id,
        title = this.title,
        message = this.message,
        authorName = this.author.fullName,
        authorCountry = this.author.country,
        authorCity = this.author.city,
        authorPhoto = this.author.photo,
        enjoyment = this.enjoyment,
        rating = this.rating,
        createdDate = this.created,
        travelerType = this.travelerType
    )

    companion object {

        fun empty() = ReviewRemote(
            id = -1,
            author = AuthorRemote.empty(),
            title = "",
            message = "",
            enjoyment = "",
            isAnonymous = false,
            rating = -1,
            created = "",
            language = "",
            travelerType = ""
        )
    }
}

data class AuthorRemote(

    @Json(name = "fullName") val fullName: String,
    @Json(name = "country") val country: String,
    @Json(name = "city") val city: String,
    @Json(name = "photo") val photo: String
) {

    fun mapperToAuthorView() = AuthorView(
        fullName = this.fullName,
        country = this.country,
        city = this.city,
        photo = this.photo
    )

    companion object {

        fun empty() = AuthorRemote(
            fullName = "",
            country = "",
            city = "",
            photo = ""
        )
    }
}

data class PaginationRemote(

    @Json(name = "limit") val limit: Int,
    @Json(name = "offset") val offset: Int
) {

    fun mapperToPaginationView() = PaginationView(
        limit = this.limit,
        offset = this.offset
    )
}