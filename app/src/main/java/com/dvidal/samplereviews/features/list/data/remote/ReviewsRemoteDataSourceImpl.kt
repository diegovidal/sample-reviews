package com.dvidal.samplereviews.features.list.data.remote

import com.dvidal.samplereviews.core.common.BaseRequester
import com.dvidal.samplereviews.core.common.EitherResult
import com.dvidal.samplereviews.core.datasource.remote.NetworkHandler
import com.dvidal.samplereviews.core.datasource.remote.RemoteApi
import com.dvidal.samplereviews.features.list.presentation.ReviewsPageView
import javax.inject.Inject

/**
 * @author diegovidal on 2020-02-15.
 */
class ReviewsRemoteDataSourceImpl @Inject constructor(
    private val remoteApi: RemoteApi,
    networkHandler: NetworkHandler
) : BaseRequester(networkHandler), ReviewsRemoteDataSource {

    override suspend fun fetchReviews(limit: Int, offset: Int): EitherResult<ReviewsPageView> {

        return request(
            apiCall = { remoteApi.fetchReviews(limit, offset) },
            transform = { response -> response.mapperToReviewsPageView() },
            default = ReviewsRemoteResponse.empty()
        )
    }
}