package com.dvidal.samplereviews.features.list.data.remote

import com.squareup.moshi.Json

/**
 * @author diegovidal on 2020-02-15.
 */


data class ReviewsRemoteResponse(

    @Json(name = "reviews") val reviews: List<ReviewsRemote>,
    @Json(name = "totalCount") val totalCount: Int,
    @Json(name = "averageRating") val averageRating: Double,
    @Json(name = "pagination") val pagination: PaginationRemote
)

data class ReviewsRemote(

    @Json(name = "id") val id: Int,
    @Json(name = "author") val author: AuthorRemote,
    @Json(name = "title") val title: String,
    @Json(name = "message") val message: String,
    @Json(name = "enjoyment") val enjoyment: String,
    @Json(name = "isAnonymous") val isAnonymous: Boolean,
    @Json(name = "rating") val rating: Int,
    @Json(name = "created") val created: String,
    @Json(name = "language") val language: String
)

data class AuthorRemote(

    @Json(name = "fullName") val fullName: String,
    @Json(name = "country") val country: String,
    @Json(name = "photo") val photo: String
)

data class PaginationRemote(

    @Json(name = "limit") val limit: Int,
    @Json(name = "offset") val offset: Int
)