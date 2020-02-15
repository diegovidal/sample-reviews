package com.dvidal.samplereviews.features.list.data.local.config

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.EitherResult

/**
 * @author diegovidal on 2020-02-14.
 */
interface ConfigLocalDataSource {


    suspend fun insertConfig(activityName: String, numReviews: Int, averageRating: Double): EitherResult<Unit>

    suspend fun fetchConfig(): EitherResult<LiveData<ConfigDto>>

    suspend fun incrementOffsetPage(): EitherResult<Unit>

    suspend fun toggleSortByRating(): EitherResult<Unit>

    suspend fun clearConfig(): EitherResult<Unit>
}