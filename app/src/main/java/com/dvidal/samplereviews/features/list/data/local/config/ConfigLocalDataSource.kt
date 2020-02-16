package com.dvidal.samplereviews.features.list.data.local.config

import androidx.lifecycle.LiveData
import com.dvidal.samplereviews.core.common.EitherResult

/**
 * @author diegovidal on 2020-02-14.
 */
interface ConfigLocalDataSource {


    suspend fun insertConfig(configDto: ConfigDto): EitherResult<Unit>

    suspend fun fetchConfig(): EitherResult<ConfigDto?>

    fun fetchConfigAsLiveData(): EitherResult<LiveData<ConfigDto?>>

    suspend fun incrementOffsetPage(): EitherResult<Unit>

    suspend fun toggleSortByRating(): EitherResult<Unit>

    suspend fun clearConfig(): EitherResult<Unit>
}